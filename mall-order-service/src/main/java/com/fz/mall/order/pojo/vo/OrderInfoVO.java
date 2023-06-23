package com.fz.mall.order.pojo.vo;

import com.fz.mall.api.user.dto.MemberReceiveAddressDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Data
public class OrderInfoVO {


    List<MemberReceiveAddressDTO> memberAddressVos;

    List<OrderItemInfoVO> items;


    private Integer integration = 0;

    /**
     * 防止重复提交订单
     */
    private String orderToken;


    Map<Long, Boolean> stocks;


    BigDecimal total;


    BigDecimal payPrice;

    private Boolean seckill;

    private Long seckillSkuId;

    private Integer seckillQuantity;

    private Long seckillSessionId;

    private String seckillCode;

    public Integer getCount() {
        Integer count = 0;
        if (items != null && items.size() > 0) {
            for (OrderItemInfoVO item : items) {
                count += item.getCount();
            }
        }
        return count;
    }


    public BigDecimal getTotal() {
        BigDecimal totalNum = BigDecimal.ZERO;
        if (items != null && items.size() > 0) {
            for (OrderItemInfoVO item : items) {
                //计算当前商品的总价格
                BigDecimal itemPrice = item.getPrice().multiply(new BigDecimal(item.getCount().toString()));
                //再计算全部商品的总价格
                totalNum = totalNum.add(itemPrice);
            }
        }
        return totalNum;
    }


    public BigDecimal getPayPrice() {
        return getTotal();
    }
}
