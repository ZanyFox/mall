package com.fz.mall.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("ums_user_oauth")
public class UserOauth {

    private Long id;
    private String oid;
    private Long uid;

}
