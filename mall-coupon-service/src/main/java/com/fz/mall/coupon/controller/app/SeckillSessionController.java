package com.fz.mall.coupon.controller.app;

import com.fz.mall.common.resp.ServerResponseEntity;
import com.fz.mall.coupon.service.SeckillSessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 秒杀活动场次 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/coupon/seckill-session")
public class SeckillSessionController {

    private final SeckillSessionService seckillSessionService;

    @PostMapping("/upload")
    public ServerResponseEntity uploadSeckillSku() {
        seckillSessionService.uploadSeckillSku();
        return ServerResponseEntity.success("上架秒杀商品成功");
    }
}
