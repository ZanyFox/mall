package com.fz.mall.goods.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.common.redis.constant.GoodsCacheConstant;
import com.fz.mall.goods.pojo.entity.Category;
import com.fz.mall.goods.mapper.CategoryMapper;
import com.fz.mall.goods.service.CategoryBrandRelationService;
import com.fz.mall.goods.service.CategoryService;
import com.fz.mall.goods.pojo.vo.CategoryTitleVO;
import com.fz.mall.goods.pojo.vo.CategoryDetailVO;
import com.github.benmanes.caffeine.cache.Cache;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */

@Slf4j
@Service
@AllArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private CategoryMapper categoryMapper;

    private CategoryBrandRelationService categoryBrandRelationService;

    private StringRedisTemplate redisTemplate;

    private Cache<String, Object> caffeineCache;

    @Override
    public List<Category> listAll() {
        List<Category> categories = list();

        return categories.stream()
                .filter(category -> category.getParentCid() == 0)
                .peek(category -> category.setChildren(getChildCategory(categories, category.getCatId())))
                // 排序  nullFirst表示null值要小于非null值
                .sorted(Comparator.comparing(Category::getSort, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }

    @Override
    public void removeCategoryByIds(List<Long> ids) {

        categoryMapper.deleteBatchIds(ids);
    }

    /**
     * @Cacheable value指定缓存的名字 最终redis中缓存的key 为value::key
     * key 使用SpEL表达式 其中想使用字符串的话用单引号
     * sync true 调用同步方法获取缓存
     */
//    @Cacheable(value = GoodsCacheConstant.GOODS_CATEGORY_PREFIX, key = "'menu'",sync = true)
//    @Override
//    public List<Category> getCategoryMenuList() {
//        return lambdaQuery().eq(Category::getCatLevel, 1).list();
//    }

    /**
     * 使用Caffeine作为JVM进程缓存
     * @return
     */
    @Override
    public List<Category> getCategoryMenuList() {

        Object o = caffeineCache.get(GoodsCacheConstant.GOODS_CATEGORY_MENU, key -> {
            String categoryMenuJson = redisTemplate.opsForValue().get(GoodsCacheConstant.GOODS_CATEGORY_MENU);
            return JSON.parseObject(categoryMenuJson, List.class);
        });
        return (List<Category>) o;
    }


    private List<CategoryTitleVO> buildCategoryTitlesWithDetail(List<Category> categories, Long categoryMenuId) {

        return categories.stream()
                .filter(c2 -> Objects.equals(c2.getParentCid(), categoryMenuId)).map(item -> {
                    List<CategoryDetailVO> categoryDetailVos = categories.stream()
                            .filter(c -> Objects.equals(c.getParentCid(), item.getCatId()))
                            .map(c3 -> {
                                CategoryDetailVO categoryDetailVo = new CategoryDetailVO();
                                categoryDetailVo.setId(c3.getCatId());
                                categoryDetailVo.setCategoryTitleId(c3.getParentCid());
                                categoryDetailVo.setName(c3.getName());
                                return categoryDetailVo;
                            }).collect(Collectors.toList());

                    CategoryTitleVO categoryTitleVO = new CategoryTitleVO();
                    categoryTitleVO.setId(item.getCatId());
                    categoryTitleVO.setCategoryMenuId(item.getParentCid());
                    categoryTitleVO.setName(item.getName());
                    categoryTitleVO.setCategoryDetailList(categoryDetailVos);
                    return categoryTitleVO;
                }).collect(Collectors.toList());

    }


    @Cacheable(value = GoodsCacheConstant.GOODS_CATEGORY_TITLE, key = "'map'")
    @Override
    public Map<String, List<CategoryTitleVO>> getCategoryTitleMap() {

        List<Category> categories = list();
        return categories.stream().filter(c1 -> c1.getParentCid() == 0)
                .collect(Collectors.toMap(c1 -> c1.getCatId().toString(), c1 -> buildCategoryTitlesWithDetail(categories, c1.getCatId())));
    }


    /**
     * @CacheEvict 删除缓存  指定删除缓存的名字和key  allEntries = true 删除某个分区(前缀)的所有缓存
     * @Caching 可以组合多个注解
     */
//    第一种方式清除多个缓存
//    @Caching(evict = {
//            @CacheEvict(value = "category", key = "'getLevelOneCategory'"),
//            @CacheEvict(value = "category", key = "'getCatalog'")
//    })

//   双写模式可以使用该注解  将返回的数据写入缓存
//    @CachePut
//    失效模式  删除缓存而不是直接修改
//    @CacheEvict(value = "category", allEntries = true)

    /**
     * 更新分类时 需要更新品牌分类关系表中的分类名称
     *
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(Category category) {
        updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Override
    public Long[] findCategoryPath(Long categoryId) {

        List<Long> path = new ArrayList<>();
        findCategoryPath(categoryId, path);
        Collections.reverse(path);
        return path.toArray(new Long[0]);
    }


    private void findCategoryPath(Long categoryId, List<Long> path) {
        path.add(categoryId);
        Category category = getById(categoryId);
        if (category != null && category.getParentCid() != 0)
            findCategoryPath(category.getParentCid(), path);
    }


    /*
        递归获取三级分类
    */
    private List<Category> getChildCategory(List<Category> all, Long categoryId) {

        return all.stream().filter(c -> Objects.equals(c.getParentCid(), categoryId))
                .peek(c -> c.setChildren(getChildCategory(all, c.getCatId())))
                .sorted(Comparator.comparing(Category::getSort, Comparator.nullsFirst(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }


    private List<Category> getChildCategory2(List<Category> categories, Long categoryId) {

        List<Category> categoryList = categories.stream()
                .filter(category -> Objects.equals(categoryId, category.getParentCid()))
                .sorted(Comparator.comparingInt(c -> (c.getSort() == null ? 0 : c.getSort())))
                .collect(Collectors.toList());
        if (categoryList.size() == 0) {
            return Collections.emptyList();
        } else {
            categoryList.forEach(category -> category.setChildren(getChildCategory2(categories, category.getCatId())));
            return categoryList;
        }
    }
}
