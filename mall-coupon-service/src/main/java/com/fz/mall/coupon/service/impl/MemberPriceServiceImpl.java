package com.fz.mall.coupon.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fz.mall.coupon.entity.MemberPrice;
import com.fz.mall.coupon.mapper.MemberPriceMapper;
import com.fz.mall.coupon.service.MemberPriceService;
import org.springframework.stereotype.Service;

@Service
public class MemberPriceServiceImpl extends ServiceImpl<MemberPriceMapper, MemberPrice> implements MemberPriceService {
}
