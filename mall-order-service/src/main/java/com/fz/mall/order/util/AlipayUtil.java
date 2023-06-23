package com.fz.mall.order.util;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;

import java.util.HashMap;
import java.util.Map;


public class AlipayUtil {

    private final static String APP_ID = "9021000122684764";

    private final static String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCtEIUwR1mj2eqmhT4Fo9r8fCKF4YGH6XDsETTLbLQdEph1m/Ob9DG+hjz8TRP9WiE2PI1QGPEFHA/QIxisUlFei9Q9TJDIGGKUGhGA9gVukiAb14I53IZJBTaCfv6U+G4oF7azj7wSvoena1p2TQ1eEf+bTK6kE8AvgFVBO+FqjPRdFAcjb2mP3v8LTYDV1tk4YNBECYNxWynTWPsbf7DTx5JqbDA1wR0CKh3fbAG8cXpJHwq6zkceTqGZI95Afa8LCCWPXoTJ9TTajZZzAK/3dqBw1N2tN4mScls6TE1BuYTEtH7JPW5B8G/Vnrdbjm2BqMri2ka11X7UO5WS4i9bAgMBAAECggEBAJy9WJMoG8RtCbr08/4xr5R6bN3qJeFNCEuVLcaLVfj6rX/e1VxKZn8Snss2l8MeTPQNFUMUw70H9D5ieuceyrkhVQCLhpjilO1jSrUMnFDPISt2HGiBFFGv2fWIRNPwx/uhtoObyw8C5mM3tD9ue4vWKzSg146coLOkvBIPxJE1Ry+u3/3tGuo+9nF67SaMXThNuGb8MftRPoVXpQ9AfdQhLyr0PfFssL42+HxYIc6EsiXjkIfVlMLl7kJrS9GvxwojK3UEwW6UmU7gru0UpTtqZRyXN9nf8hUMb/FOS3M/IiFgH5z3acE2GN9DIPsaof1vnNnQ/TGevOwDOaNwYjkCgYEA5XIbOti/i6KdHv0u1+jF2ROWgfC/rM76B/u4dbpANYo+cwqMuI9+7/m+dw+MNMyCSREpHHF+jMN8n28f759TnQG1Z7dlN1jJbVw5Gcml2Z7vD0Q4JWIWdHe6kmMTVX5wzgSZmdZohFyR+xKKme+eo7snhoYC0VA7UyGByxdPSrcCgYEAwRf7frxUpITRWpc+DTyKWUUGr3OnuGbsXb5TecCcbVT4LFfbh6/OCKuNtWbRrLmHEG2sDi31LFvr5zkx4LqNdg0625Xjfm6uqKCbEPY7bskMrqZLWSxoi5VYAmdOlNu9oCb5XWgmfdWnJce9r16KxJhcjaOkB6qaNz7GCGgG7H0CgYA2qGQMNK4MmYRMaYvLK0xWVfA4rB09EWFGnZRnlyLMnk4aIZ08KhLXH+tbeXyw5zbyKQBeIjs/VSJO6K3260GD2XBZcLrP4Hq7boi7t+mmWdIhFOM7JI6s2Ai97pQgfappqXbdytHDFjpAUT5rRUXJQ0A7L5lkO3SmbQj6VGczKQKBgAk6WC6KWZEzN74YlEKVrhbuEY+4+z74zDr+qYacCd2/vcyAtmEZqcMIT9FnhIW4qBfE5gAXeiwQiImYViXjRHdr0nBEDYcxSu+MHV0GJ3j36r89+0Pbt3NUQ8x5m2vJxJouwqQQIibJWx+XGB2VaxRSI7oxI9mp8YzKVzR3pD0RAoGBAL1hZIgIJAZDYn5INGLazE9BxAzhv+2qFontbRLWAOMJ1fEN5TgLtkFUsh/XcPzq4F1HogvdZEGfqeQeAbiRikxf+Yq24blZDs4cl4iZvHzrgTCPMQsl1Fo0iGiSUxZ7CoDHw6iU6QSHFIwzv1cp7IBxL8+dtedYanV9x2ZPcI5Z";

    private final static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyQQceVUChTJGtF/a8SXufhSxDTKporieTq9NO7yDZSpDlAX1zVPT/nf0KWAlxq1TYappWMIYtyrOABhJyn6flNP6vuSBiM5lYsepHvYrtRHqlFiJruEkiaCgEZBKL5aCfBHYj0oqgQn9MpNV/PEH4cBYAVaiI4+VX8CBUQfeEGjgN6OkpLULZ3X0JUkmSnVvCNJ1m3PD68IIlbOfEZXJUKCqmZhzprGR5VWswjxA+g87cMwvijL4gdkSy/daG62Bz5vApcmmMkuX1k1fMWP4ajZCASVw8HD+MSLRhd8We9F97gd8CW0TavzbdR+mTS5H4yEgO8F9HRAsbkhV9yu0yQIDAQAB";

    private final static String RETURN_URL = "http://order.mallmall.com/paySuccess.html";

    private final static String SIGN_TYPE = "RSA2";

    private final static String CHARSET = "utf-8";

    private final static String GATEWAY_URL = "https://openapi-sandbox.dl.alipaydev.com/gateway.do";

    public static String pay(String orderSn, String subject, String price) throws AlipayApiException {

        AlipayClient alipayClient = new DefaultAlipayClient(GATEWAY_URL,
                APP_ID, RSA_PRIVATE_KEY, "json",
                CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(RETURN_URL);
//        alipayRequest.setNotifyUrl(notifyUrl);

        Map<String, String> payParam = new HashMap<>();
        payParam.put("app_id", APP_ID);
        payParam.put("method", "alipay.trade.page.pay");
        payParam.put("out_trade_no", orderSn);
        payParam.put("subject", "subject");
        payParam.put("total_amount", price);
        payParam.put("timeout_express", "1m");
        payParam.put("product_code", "FAST_INSTANT_TRADE_PAY");

        alipayRequest.setBizContent(JSON.toJSONString(payParam));

        return alipayClient.pageExecute(alipayRequest).getBody();

    }

    public static boolean rsaCheck(Map<String, String> params) throws AlipayApiException {
        return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, "RSA2");
    }

}
