package com.fz.mall.stock;

import com.fz.mall.common.rabbitmq.constant.StockMqConstants;
import com.fz.mall.stock.mapper.WareOrderTaskDetailMapper;
import com.fz.mall.stock.mapper.WareSkuMapper;
import com.fz.mall.stock.pojo.entity.WareOrderTaskDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WareStockTest {

    @Autowired
    private WareSkuMapper skuMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private WareOrderTaskDetailMapper wareOrderTaskDetailMapper;

    @Test
    public void testSkuHasStock() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
//        List<Map<String, Object>> maps = skuMapper.selectSkuHasStockBySkuId(ids);
//        Map<Long, Boolean> collect = maps.stream().collect(Collectors.toMap(item -> ((Long) item.get("skuId")), item -> ((Boolean) item.get("hasStock"))));
//        System.out.println(collect);
//        Map<Long, Integer> skuStockBySkuId = skuMapper.getSkuStockBySkuId(ids);
//        System.out.println(skuStockBySkuId);


    }

    @Test
    public void testGetWareIdHasStockBySkuId() {
//        List<SkuQuantity> list = new ArrayList<>();
//
//        SkuQuantity skuQuantity = new SkuQuantity();
//        skuQuantity.setSkuId(1L);
//        skuQuantity.setQuantity(10);
//
//        SkuQuantity skuQuantity1 = new SkuQuantity();
//        skuQuantity1.setSkuId(2L);
//        skuQuantity1.setQuantity(5);
//        list.add(skuQuantity);
//        list.add(skuQuantity1);
//
//        List<WareHasStock> wareIdHasStockBySkuId = skuMapper.getWareIdHasStockBySkuId(list);
//        Map<Long, List<Long>> collect = wareIdHasStockBySkuId.stream().collect(Collectors.toMap(WareHasStock::getSkuId, WareHasStock::getWareIds));
//        System.out.println(collect);

    }

    @Test
    public void testUpdateStockUnlock() {

        WareOrderTaskDetail wareOrderTaskDetail = new WareOrderTaskDetail();
        wareOrderTaskDetail.setSkuId(32L);
        wareOrderTaskDetail.setWareId(1L);
        wareOrderTaskDetail.setQuantity(10);

        WareOrderTaskDetail wareOrderTaskDetail1 = new WareOrderTaskDetail();
        wareOrderTaskDetail1.setSkuId(2L);
        wareOrderTaskDetail1.setWareId(3L);
        wareOrderTaskDetail1.setQuantity(5);

        List<WareOrderTaskDetail> wareOrderTaskDetails = new ArrayList<>();
        wareOrderTaskDetails.add(wareOrderTaskDetail);
        wareOrderTaskDetails.add(wareOrderTaskDetail1);

//        int i = skuMapper.updateStockUnlock(wareOrderTaskDetails);
//        System.out.println(i);
    }

    @Test
    public void testAmqp() {
//        rabbitTemplate.convertAndSend(StockMqConstants.STOCK_EXCHANGE, StockMqConstants.UNLOCK_STOCK_DELAY_ROUTING_KEY, "hello");

        System.out.println(wareOrderTaskDetailMapper.getWareOrderTaskDetailByOrderSn("202306081946090991666773604396175361"));
    }


}
