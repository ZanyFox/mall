package com.fz.mall.goods;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fz.mall.api.goods.dto.CartSkuInfoDTO;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.mapper.AttrAttrGroupRelationMapper;
import com.fz.mall.goods.mapper.AttrGroupMapper;
import com.fz.mall.goods.mapper.SkuInfoMapper;
import com.fz.mall.goods.mapper.SkuSaleAttrValueMapper;
import com.fz.mall.goods.pojo.dto.AttrGroupRelationDTO;
import com.fz.mall.goods.pojo.entity.AttrGroup;
import com.fz.mall.goods.pojo.entity.Category;
import com.fz.mall.goods.pojo.entity.SkuSaleAttrValue;
import com.fz.mall.goods.pojo.vo.CategoryTitleVO;
import com.fz.mall.goods.pojo.vo.SkuItemVO;
import com.fz.mall.goods.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
//2.3版本需要加这个注解
@RunWith(SpringRunner.class)
public class TestGoods {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private AttrAttrGroupRelationMapper attrAttrGroupRelationMapper;
    @Autowired
    private AttrService attrService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private AttrGroupMapper attrGroupMapper;

    @Autowired
    private SkuItemService skuItemService;

    @Autowired
    private SkuInfoMapper skuInfoMapper;


    @Test
    public void testGoodsService() {
        List<Category> categories = categoryService.getCategoryMenuList();
        System.out.println(categories);
    }

    @Test
    public void testStream() {

        List<Integer> integers = new ArrayList<>();
        List<String> strings = integers.stream().map(i -> i.toString()).collect(Collectors.toList());
    }


    @Test
    public void testRedis() {
        Set<String> keys = redisTemplate.keys("*");
        assert keys != null;
        keys.forEach(System.out::println);
    }

    @Test
    public void testObjectMapper() throws JsonProcessingException {

        Map<String, List<CategoryTitleVO>> catalog = categoryService.getCategoryTitleMap();
        String s = objectMapper.writeValueAsString(catalog);
        Map<String, List<CategoryTitleVO>> stringListMap = objectMapper.readValue(s, new TypeReference<Map<String, List<CategoryTitleVO>>>() {});
        System.out.println(stringListMap);
    }

    @Test
    public void testAttrGroup() {
        SimplePageDTO simplePageDTO = new SimplePageDTO();
        simplePageDTO.setPage(1L);
        simplePageDTO.setSize(10L);
        simplePageDTO.setKey("phone");
        PageVO<AttrGroup> page = attrGroupService.page(1L, simplePageDTO);
        System.out.println(page);
    }

    @Test
    public void testAttr() {

        List<AttrGroupRelationDTO> relationDTOS = new ArrayList<>();
        AttrGroupRelationDTO relationDTO = new AttrGroupRelationDTO();
        relationDTO.setAttrId(1L);
        relationDTO.setAttrGroupId(1L);
        AttrGroupRelationDTO relationDTO1 = new AttrGroupRelationDTO();
        relationDTO1.setAttrId(1L);
        relationDTO1.setAttrGroupId(1L);
        relationDTOS.add(relationDTO1);
        attrAttrGroupRelationMapper.deleteBatch(relationDTOS);
    }


    @Test
    public void testGetSearchSkuSaleAttrValueBySkuId() {
        List<SkuSaleAttrValue> attrs = skuSaleAttrValueService.getSearchSkuSaleAttrValueBySkuId(31L);
        System.out.println(attrs);
    }

    @Test
    public void testSelectSaleAttrsBySpuId() {

        System.out.println(SkuItemVO.SkuSaleAttrVO.class.getName());
        List<SkuItemVO.SkuSaleAttrVO> saleAttrs = skuSaleAttrValueMapper.getPurchaseAttrsBySpuId(10L);
        System.out.println(saleAttrs);
    }

    @Test
    public void testGetAttrGroupWithBaseAttrs() {

        List<SkuItemVO.AttrGroupVO> attrs = attrGroupMapper.getAttrGroupWithSaleAttrs(225L, 24L);
        System.out.println(attrs);
    }

    @Test
    public void testGetSkuAttrGroupWithAllAttrs() {
        List<SkuItemVO.AttrGroupVO> attrs = attrGroupService.getSkuAttrGroupWithAllAttrs(225L, 24L, 10L);
        System.out.println(attrs);
    }

    @Test
    public void testGetSkuItemById() {

//        SkuItemVO skuItemVO = skuItemService.getSkuItemById(24L);
//        System.out.println(skuItemVO);

//        List<String> saleAttrListBySkuId = skuSaleAttrValueMapper.getSaleAttrListBySkuId(32L);
//        System.out.println(saleAttrListBySkuId);


        List<CartSkuInfoDTO> cartSkusByIds = skuInfoMapper.getCartSkusByIds(Arrays.asList(32L, 33L));
        System.out.println(cartSkusByIds);
    }

    @Test
    public void testGetSaleAttrListBySkuId() {
        System.out.println(skuSaleAttrValueMapper.getSaleAttrValuesBySkuIds(Arrays.asList(100L, 101L)));
    }

}
