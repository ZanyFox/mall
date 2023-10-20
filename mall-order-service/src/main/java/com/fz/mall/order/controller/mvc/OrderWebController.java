package com.fz.mall.order.controller.mvc;

import com.alipay.api.AlipayApiException;
import com.fz.mall.common.exception.MallServerException;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.order.pojo.dto.PaySyncResultDTO;
import com.fz.mall.order.pojo.param.OrderSubmitParam;
import com.fz.mall.order.pojo.vo.OrderInfoVO;
import com.fz.mall.order.pojo.vo.OrderListItemVO;
import com.fz.mall.order.pojo.vo.OrderSubmitResponseVO;
import com.fz.mall.order.service.OrderSeckillService;
import com.fz.mall.order.service.OrderService;
import com.fz.mall.order.util.AlipayUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@AllArgsConstructor
public class OrderWebController {


    private OrderService orderService;

    private OrderSeckillService orderSeckillService;

    @RequestMapping("list.html")
    public String orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "60") Integer size,
                            Model model) {

        PageVO<OrderListItemVO> pageVO = orderService.page(page, size);
        model.addAttribute("page", pageVO);
        return "orderList";
    }

    @RequestMapping("/auction/order/confirm_order.html")
    public String confirmOrder(Model model) {

        OrderInfoVO orderInfo = orderService.getOrderInfo();
        model.addAttribute("confirmOrderData", orderInfo);
        return "confirm";
    }

    @RequestMapping("/order/confirm_order.html")
    public String confirmSeckillOrder(String killId, String seckillCode, Integer quantity, Model model) {

        OrderInfoVO orderInfoVO = orderSeckillService.getSeckillOrderInfo(killId, seckillCode, quantity);
        model.addAttribute("confirmOrderData", orderInfoVO);
        return "confirm";
    }

    @RequestMapping("/submitOrder")
    public String submitOrder(OrderSubmitParam orderSubmitParam, Model model) {

        log.info("提交信息: {}", orderSubmitParam.toString());
        OrderSubmitResponseVO orderSubmitResponseVO = null;
        if (orderSubmitParam.getSeckillSkuId() != null)
            orderSubmitResponseVO = orderService.submitSeckillOrder(orderSubmitParam);
        else orderSubmitResponseVO = orderService.tryGenerateOrder(orderSubmitParam);
        model.addAttribute("submitOrderResp", orderSubmitResponseVO);
        return "pay";
    }

    @ResponseBody
    @RequestMapping(value = "/aliPay", produces = {MediaType.TEXT_HTML_VALUE})
    public String alipay(String orderSn) {
        return orderService.pay(orderSn);
    }


    @RequestMapping("paySuccess.html")
    public String paySuccess(PaySyncResultDTO paySyncResultDTO, HttpServletRequest request) {

        log.info("result: {}", paySyncResultDTO);
        Map<String, String[]> requestParams = request.getParameterMap();
        Map<String, String> params = new HashMap<>();
        for (String o : requestParams.keySet()) {
            String[] values = requestParams.get(o);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(o, valueStr);
        }

        try {
            AlipayUtil.rsaCheck(params);
            orderService.handlePayResult(paySyncResultDTO);
        } catch (AlipayApiException | MallServerException e) {
            e.printStackTrace();
            log.info(e.getCause().getMessage());
            return "payFail";
        }

        return "paySuccess";
    }

}
