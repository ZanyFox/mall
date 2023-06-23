package com.fz.mall.goods.controller.app;

import com.fz.mall.goods.service.AttrGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 属性分组 前端控制器
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@RestController
@RequestMapping("/goods/attr-group")
public class AttrGroupController {

    @Autowired
    private AttrGroupService attrGroupService;


}
