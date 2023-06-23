package com.fz.mall.api.user.dto;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 会员等级
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
@Data
public class MemberLevelDTO{



    private Long id;

    private String name;

    private Integer growthPoint;

    private Byte defaultStatus;

    private BigDecimal freeFreightPoint;

    private Integer commentGrowthPoint;

    private Byte privilegeFreeFreight;


    private Byte privilegeMemberPrice;

    private Byte privilegeBirthday;


    private String note;
}
