package com.fz.mall.coupon.service.impl;

import com.fz.mall.api.coupon.dto.MemberPriceDTO;
import com.fz.mall.api.coupon.dto.SkuReductionDTO;
import com.fz.mall.coupon.entity.MemberPrice;
import com.fz.mall.coupon.entity.SkuFullReduction;
import com.fz.mall.coupon.entity.SkuLadder;
import com.fz.mall.coupon.mapper.SkuFullReductionMapper;
import com.fz.mall.coupon.service.MemberPriceService;
import com.fz.mall.coupon.service.SkuFullReductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品满减信息 服务实现类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@Service
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionMapper, SkuFullReduction> implements SkuFullReductionService {

    @Autowired
    private SkuLadderService skuLadderService;

    @Autowired
    private MemberPriceService memberPriceService;

    @Override
    public void save(SkuReductionDTO skuReductionDTO) {
        // 保存满减打折 会员价
        if (skuReductionDTO.getFullCount() > 0) {
            // 阶梯价格
            SkuLadder skuLadder = new SkuLadder();
            BeanUtils.copyProperties(skuLadder, skuReductionDTO);
            skuLadderService.save(skuLadder);
        }


        // 满减打折
        if (skuReductionDTO.getFullPrice().compareTo(BigDecimal.ZERO) > 0) {
            SkuFullReduction skuFullReduction = new SkuFullReduction();
            BeanUtils.copyProperties(skuReductionDTO, skuFullReduction);
            save(skuFullReduction);
        }


        // 会员价
        List<MemberPriceDTO> memberPrices = skuReductionDTO.getMemberPrice();
        if (!CollectionUtils.isEmpty(memberPrices)) {
            List<MemberPrice> memberPriceList = memberPrices.stream()
                    .filter(memberPriceDTO -> memberPriceDTO.getPrice().compareTo(BigDecimal.ZERO) > 0)
                    .map(memberPriceDTO -> {
                        MemberPrice memberPrice = new MemberPrice();
                        memberPrice.setSkuId(skuReductionDTO.getSkuId());
                        memberPrice.setMemberLevelId(memberPriceDTO.getId());
                        memberPrice.setMemberLevelName(memberPriceDTO.getName());
                        memberPrice.setMemberPrice(memberPriceDTO.getPrice());
                        memberPrice.setAddOther(1);
                        return memberPrice;
                    }).collect(Collectors.toList());
            memberPriceService.saveBatch(memberPriceList);
        }
    }
}
