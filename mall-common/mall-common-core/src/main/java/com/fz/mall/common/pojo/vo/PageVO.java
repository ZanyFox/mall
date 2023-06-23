package com.fz.mall.common.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVO<T> implements Serializable {

    /**
     * 当前页
     */
    private Long page;
    /**
     * 当前页条目数
     */
    private Long size;
    /**
     * 总页数
     */
    private Long pages;
    /**
     * 总条目数
     */
    private Long total;
    private List<T> datas;
}
