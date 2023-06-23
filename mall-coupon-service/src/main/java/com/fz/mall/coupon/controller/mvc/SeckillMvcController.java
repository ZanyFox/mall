package com.fz.mall.coupon.controller.mvc;

import com.fz.mall.coupon.service.SeckillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@AllArgsConstructor
public class SeckillMvcController {


    private final SeckillService seckillService;

    @RequestMapping("/seckill")
    public String seckill(String killId, String key, Integer num) {

        seckillService.seckill(killId, key, num);
        return "success";
    }

}
