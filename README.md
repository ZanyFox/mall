
## 💎项目简介

本项目是基于Spring Cloud、Nacos、Seata、MySQL、MybatisPlus、Redis、RabbitMQ、ElasticSearch、Thymeleaf 等技术构建的分布式商城系统。



## ✨技术亮点

- 使用docker部署

- Lua脚本+消息队列实现秒杀功能

- 消息队列保证分布式事务最终一致性

- 本地缓存+redis+定时任务更新商品分类

- 可使用gitee授权登录

  

## 🗂项目目录

``` text
mall
├── mall-api            # Feign调用接口
├── mall-common         # 公共依赖库
├── mall-gateway        # 网关服务
├── mall-auth-service   # 登录注册服务
├── mall-biz-service    # 通用第三方服务
├── mall-cart-service   # 购物车服务
├── mall-coupon-service # 优惠券服务
├── mall-goods-service  # 商品服务
├── mall-order-service  # 订单服务
├── mall-search-service # 搜索服务
├── mall-stock-service  # 库存服务
├── mall-user-service   # 用户服务
```

## 🧩项目截图

