package com.fz.mall.coupon.controller.app;

import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.coupon.pojo.SeckillSkuVO;
import com.fz.mall.coupon.service.SeckillService;
import com.fz.mall.coupon.service.SeckillSessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/coupon/seckill")
@AllArgsConstructor
public class SeckillController {

    private final SeckillService seckillService;

    private final SeckillSessionService seckillSessionService;


    @PostMapping("/upload")
    public ServerResponseEntity uploadSeckillSku() {
        seckillSessionService.uploadSeckillSku();
        return ServerResponseEntity.success("上架秒杀商品成功");
    }

    @GetMapping
    public ServerResponseEntity getSeckillSkus() {
        List<SeckillSkuVO> seckillSkus = seckillService.getSeckillSkus();
        return ServerResponseEntity.success(seckillSkus);
    }


    @PostMapping("/seckill")
    public ServerResponseEntity seckill(String killId, String key, Integer num) {

        seckillService.seckill(killId, key, num);
        return ServerResponseEntity.success("秒杀成功");
    }
}
