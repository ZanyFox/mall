package com.fz.mall.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.api.coupon.dto.SkuReductionDTO;
import com.fz.mall.api.coupon.dto.SpuBoundDTO;
import com.fz.mall.api.coupon.feign.CouponFeignClient;
import com.fz.mall.api.feign.StockFeignClient;
import com.fz.mall.common.data.bo.EsSkuBO;
import com.fz.mall.common.database.util.PageUtil;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.common.rabbitmq.constant.GoodsListenerConstants;
import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.common.resp.ResponseEnum;
import com.fz.mall.goods.constant.SpuStatusEnum;
import com.fz.mall.goods.mapper.SpuInfoMapper;
import com.fz.mall.goods.pojo.dto.*;
import com.fz.mall.goods.pojo.entity.*;
import com.fz.mall.goods.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * spu信息 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Slf4j
@Service
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoMapper, SpuInfo> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;


    @Autowired
    private CouponFeignClient couponFeignClient;


    @Autowired
    private StockFeignClient stockFeignClient;


    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private BrandService brandService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Transactional
    @Override
    public void save(SpuSaveDTO spuSaveDTO) {

        // 保存spu基本信息
        SpuInfo spuInfo = new SpuInfo();
        BeanUtils.copyProperties(spuSaveDTO, spuInfo);
        spuInfo.setCreateTime(LocalDateTime.now());
        spuInfo.setUpdateTime(LocalDateTime.now());
        spuInfo.setPublishStatus(SpuStatusEnum.CREATED.ordinal());
        save(spuInfo);

        // 保存spu图片描述
        SpuInfoDesc spuInfoDesc = new SpuInfoDesc();
        spuInfoDesc.setSpuId(spuInfo.getId());
        spuInfoDesc.setDescription(String.join(",", spuSaveDTO.getDecript()));
        spuInfoDescService.save(spuInfoDesc);
        // 保存spu图片集
        List<String> images = spuSaveDTO.getImages();
        if (!CollectionUtils.isEmpty(images)) {
            List<SpuImages> spuImagesList = images.stream().map(image -> {
                SpuImages spuImages = new SpuImages();
                spuImages.setSpuId(spuInfo.getId());
                spuImages.setImgUrl(image);
                return spuImages;
            }).collect(Collectors.toList());
            spuImagesService.saveBatch(spuImagesList);
        }

        // 保存spu规格参数
        List<BaseAttrs> baseAttrs = spuSaveDTO.getBaseAttrs();
        if (!CollectionUtils.isEmpty(baseAttrs)) {
            List<ProductAttrValue> productAttrValues = baseAttrs.stream().map(baseAttr -> {
                ProductAttrValue productAttrValue = new ProductAttrValue();
                productAttrValue.setAttrId(baseAttr.getAttrId());
                Attr attr = attrService.getById(baseAttr.getAttrId());
                productAttrValue.setAttrName(attr.getAttrName());
                productAttrValue.setAttrValue(baseAttr.getAttrValues());
                productAttrValue.setQuickShow(baseAttr.getShowDesc());
                productAttrValue.setSpuId(spuInfo.getId());
                productAttrValue.setSearchType(baseAttr.getSearchType());
                return productAttrValue;
            }).collect(Collectors.toList());

            productAttrValueService.saveBatch(productAttrValues);
        }

        // 保存sku属性
        List<Skus> skus = spuSaveDTO.getSkus();
        if (!CollectionUtils.isEmpty(skus)) {
            skus.forEach((skusVO -> {
                SkuInfo skuInfo = new SkuInfo();
                BeanUtils.copyProperties(skusVO, skuInfo);

                skuInfo.setSpuId(spuInfo.getId());
                skuInfo.setBrandId(spuInfo.getBrandId());
                skuInfo.setCategoryId(spuInfo.getCategoryId());
                skuInfo.setSaleCount(0L);

                List<Images> skusVOImages = skusVO.getImages();
                // 查找第一个默认图片
                Images defaultImageVO = skusVOImages.stream().filter(i -> i.getDefaultImg() == 1).findFirst().orElse(null);
                if (defaultImageVO != null && StringUtils.isNotEmpty(defaultImageVO.getImgUrl())) {
                    skuInfo.setSkuDefaultImg(defaultImageVO.getImgUrl());

                }
                skuInfoService.save(skuInfo);


                if (!CollectionUtils.isEmpty(skusVOImages)) {
                    List<SkuImages> skuImagesList = skusVOImages.stream()
                            .filter(skuImage -> StringUtils.isNotEmpty(skuImage.getImgUrl()))
                            .map(skuImage -> {
                                SkuImages skuImages = new SkuImages();
                                skuImages.setSkuId(skuInfo.getSkuId());
                                skuImages.setImgUrl(skuImage.getImgUrl());
                                skuImages.setDefaultImg(skuImage.getDefaultImg());
                                return skuImages;
                            }).collect(Collectors.toList());

                    skuImagesService.saveBatch(skuImagesList);
                }

                // 保存sku销售属性
                List<SimpleAttr> attrs = skusVO.getAttr();
                if (!CollectionUtils.isEmpty(attrs)) {
                    List<SkuSaleAttrValue> saleAttrValues = attrs.stream().map(simpleAttrVO -> {
                        SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
                        BeanUtils.copyProperties(simpleAttrVO, skuSaleAttrValue);
                        skuSaleAttrValue.setSkuId(skuInfo.getSkuId());
                        return skuSaleAttrValue;
                    }).collect(Collectors.toList());
                    skuSaleAttrValueService.saveBatch(saleAttrValues);
                }

                // 保存sku的优惠信息
                SkuReductionDTO skuReductionDTO = new SkuReductionDTO();
                BeanUtils.copyProperties(skusVO, skuReductionDTO);
                skuReductionDTO.setSkuId(skuInfo.getSkuId());


                // 判断满减时候有意义

                if (skuReductionDTO.getFullPrice().compareTo(BigDecimal.ZERO) > 0 || (skuReductionDTO.getFullCount() != null && skuReductionDTO.getFullCount() > 0)) {
                    ServerResponseEntity serverResponseEntity = couponFeignClient.saveSkuReduction(skuReductionDTO);
                    if (!Objects.equals(serverResponseEntity.getCode(), ResponseEnum.SUCCESS.getCode())) {
                        log.error("远程调用出现异常");
                    }
                }
            }));
        }

        Bounds bounds = spuSaveDTO.getBounds();
        SpuBoundDTO spuBoundDTO = new SpuBoundDTO();
        BeanUtils.copyProperties(bounds, spuBoundDTO);
        spuBoundDTO.setSpuId(spuInfo.getId());
        ServerResponseEntity serverResponseEntity = couponFeignClient.saveSpuBounds(spuBoundDTO);
        if (!Objects.equals(serverResponseEntity.getCode(), ResponseEnum.SUCCESS.getCode())) {
            log.error("远程调用出现异常");
        }

    }

    @Override
    public PageVO<SpuInfo> list(SpuPageDTO spuPageDTO) {

        LambdaQueryWrapper<SpuInfo> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        lambdaQueryWrapper
                .and(StringUtils.isNotBlank(spuPageDTO.getKey()),
                        query -> query.eq(SpuInfo::getId, spuPageDTO.getKey()).or().like(SpuInfo::getSpuName, spuPageDTO.getKey()))
                .eq(spuPageDTO.getBrandId() != null, SpuInfo::getBrandId, spuPageDTO.getBrandId())
                .eq(spuPageDTO.getStatus() != null, SpuInfo::getPublishStatus, spuPageDTO.getStatus())
                .eq(spuPageDTO.getCategoryId() != null, SpuInfo::getCategoryId, spuPageDTO.getCategoryId());

        Page<SpuInfo> page = page(PageUtil.newPage(spuPageDTO), lambdaQueryWrapper);
        return PageUtil.pageVO(page);
    }


    @Transactional
    @Override
    public void updateSpuStatus(UpdateSpuStatusDTO updateSpuStatusDTO) {

        spuInfoMapper.updateSpuStatus(updateSpuStatusDTO);

        switch (SpuStatusEnum.values()[updateSpuStatusDTO.getStatus()]) {
            case CREATED:
                break;
            case SALE:
                // TODO 把上架的spuId发送到MQ中
                rabbitTemplate.convertAndSend(
                        GoodsListenerConstants.SEARCH_GOODS_EXCHANGE,
                        GoodsListenerConstants.SEARCH__GOODS_INSERT_ROUTING_KEY,
                        updateSpuStatusDTO.getSpuId()
                );
                break;
            case NOT_SALE:
                // TODO 下架
                break;
        }
    }

    @Override
    public List<EsSkuBO> getEsSkuBOsBySpuId(Long spuId) {

        List<SkuInfo> skuInfos = skuInfoService.getSkusBySpuId(spuId);
        SpuInfo spuInfo = getById(spuId);
        List<ProductAttrValue> productAttrValues = productAttrValueService.listSearchAttrBySpuId(spuId);

        List<EsSkuBO.Attrs> esSkuAttrList = productAttrValues.stream().map((productAttrValue -> {
            EsSkuBO.Attrs attrs = new EsSkuBO.Attrs();
            BeanUtils.copyProperties(productAttrValue, attrs);
            return attrs;
        })).collect(Collectors.toList());


        Map<Long, Boolean> skuHasStockMap = new HashMap<>();
        List<Long> skuIds = skuInfos.stream().map(SkuInfo::getSkuId).collect(Collectors.toList());


        try {
            ServerResponseEntity<Map<Long, Boolean>> skuHasStockResponseEntity = stockFeignClient.getSkuHasStockBySkuIds(skuIds);
            skuHasStockMap = skuHasStockResponseEntity.getData();
        } catch (Exception e) {
            log.error("远程调用异常：{}", e.toString());
        }

        CategoryBrandRelation categoryBrandRelation = categoryBrandRelationService.lambdaQuery()
                .eq(CategoryBrandRelation::getBrandId, spuInfo.getBrandId())
                .eq(CategoryBrandRelation::getCategoryId, spuInfo.getCategoryId())
                .one();


        Map<Long, Boolean> finalSkuHasStockMap = skuHasStockMap;
        return skuInfos.stream().map(skuInfo -> {
            EsSkuBO esSkuBO = new EsSkuBO();
            BeanUtils.copyProperties(skuInfo, esSkuBO);
            esSkuBO.setBrandId(spuInfo.getBrandId());
            esSkuBO.setBrandName(categoryBrandRelation.getBrandName());

            Brand brand = brandService.getById(spuInfo.getBrandId());
            if (brand != null)
                esSkuBO.setBrandImg(brand.getLogo());

            esSkuBO.setCategoryId(spuInfo.getCategoryId());
            esSkuBO.setCategoryName(categoryBrandRelation.getCategoryName());
            esSkuBO.setSkuPrice(skuInfo.getPrice());
            esSkuBO.setSkuImg(skuInfo.getSkuDefaultImg());


            List<EsSkuBO.Attrs> saleAttrs = skuSaleAttrValueService
                    .getSearchSkuSaleAttrValueBySkuId(skuInfo.getSkuId())
                    .stream().map(skuSaleAttrValue -> {
                        EsSkuBO.Attrs attrs = new EsSkuBO.Attrs();
                        BeanUtils.copyProperties(skuSaleAttrValue, attrs);
                        return attrs;
                    }).collect(Collectors.toList());

            if (ObjectUtils.isNotEmpty(saleAttrs))
                saleAttrs.addAll(esSkuAttrList);

            esSkuBO.setAttrs(saleAttrs);
            System.out.println(esSkuAttrList);
            esSkuBO.setHasStock(finalSkuHasStockMap.getOrDefault(skuInfo.getSkuId(), true));
            // TODO 设置热度评分
            esSkuBO.setHotScore(0L);
            return esSkuBO;
        }).collect(Collectors.toList());

    }


}
