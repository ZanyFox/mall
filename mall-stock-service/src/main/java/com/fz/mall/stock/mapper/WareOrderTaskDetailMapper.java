package com.fz.mall.stock.mapper;

import com.fz.mall.stock.pojo.entity.WareOrderTaskDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 库存工作单 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface WareOrderTaskDetailMapper extends BaseMapper<WareOrderTaskDetail> {

    List<WareOrderTaskDetail> getWareOrderTaskDetailByOrderSn(@Param("orderSn") String orderSn);

    void updateStockItemStatus(@Param("ids") List<Long> ids, @Param("status") Integer status);

    /**
     * 更新库存详情状态
     * @param orderSn
     * @param status
     */
    void updateStatusByOrderSn(@Param("orderSn") String orderSn, @Param("status") Integer status);
}
