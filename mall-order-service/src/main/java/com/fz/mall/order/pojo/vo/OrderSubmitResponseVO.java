package com.fz.mall.order.pojo.vo;


import com.fz.mall.order.entity.Order;
import lombok.Data;



@Data
public class OrderSubmitResponseVO {

    private Order order;


    private Integer code;


}
