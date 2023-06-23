package com.fz.mall.common.database.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fz.mall.common.pojo.dto.SimplePageDTO;
import com.fz.mall.common.pojo.vo.PageVO;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PageUtil {


    public static final String ASC = "asc";

    public static <T> Page<T> newPage(SimplePageDTO simplePageDTO) {

        Page<T> page = new Page<>(simplePageDTO.getPage(), simplePageDTO.getSize());
        if (ObjectUtils.nullSafeEquals(ASC, simplePageDTO.getOrder()) && !StringUtils.isEmpty(simplePageDTO.getSort())) {
            page.addOrder(OrderItem.asc(simplePageDTO.getSort()));
        } else {
            page.addOrder(OrderItem.desc(simplePageDTO.getSort()));
        }
        return page;
    }

    public static <T> Page<T> newPage(Long page, Long size) {

        SimplePageDTO simplePageDTO = new SimplePageDTO();
        simplePageDTO.setPage(page);
        simplePageDTO.setSize(size);
        return newPage(simplePageDTO);
    }

    public static <T> PageVO<T> pageVO(IPage<?> iPage, List<T> datas) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setPage(iPage.getPages());
        pageVO.setTotal(iPage.getTotal());
        pageVO.setSize(iPage.getSize());
        pageVO.setDatas(datas);
        pageVO.setPage(iPage.getCurrent());
        return pageVO;
    }


    public static <T> PageVO<T> pageVO(IPage<T> iPage) {
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setPages(iPage.getPages());
        pageVO.setTotal(iPage.getTotal());
        pageVO.setSize(iPage.getSize());
        pageVO.setDatas(iPage.getRecords());
        pageVO.setPage(iPage.getCurrent());
        return pageVO;
    }


    public static <T, R> PageVO<R> pageVO(IPage<T> iPage, Function<T, R> function) {
        PageVO<R> pageVO = new PageVO<>();
        pageVO.setPages(iPage.getPages());
        pageVO.setTotal(iPage.getTotal());
        pageVO.setSize(iPage.getSize());
        List<R> result = iPage.getRecords().stream().map(function).collect(Collectors.toList());
        pageVO.setDatas(result);
        pageVO.setPage(iPage.getCurrent());
        return pageVO;
    }

}
