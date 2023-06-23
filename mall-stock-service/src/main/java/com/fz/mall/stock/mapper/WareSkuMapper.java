package com.fz.mall.stock.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fz.mall.api.dto.SkuQuantityDTO;
import com.fz.mall.api.dto.SkuStockDTO;
import com.fz.mall.stock.pojo.WareHasStock;
import com.fz.mall.stock.pojo.dto.SkuHasStockDTO;
import com.fz.mall.stock.pojo.entity.PurchaseDetail;
import com.fz.mall.stock.pojo.entity.WareOrderTaskDetail;
import com.fz.mall.stock.pojo.entity.WareSku;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品库存 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 20:50:39
 */
public interface WareSkuMapper extends BaseMapper<WareSku> {

    /**
     * 更新库存
     *
     * @param item
     */
    void updateStock(PurchaseDetail item);

    /**
     * 使用List<Map<String, Object>>可以映射所有结果集
     *
     * @param skuIds
     * @return
     */
    List<SkuHasStockDTO> getSkuHasStockBySkuId(@Param("skuIds") List<Long> skuIds);

    /**
     * 批量查询查询商品的总库存
     *
     * @param skuIds
     * @return
     */

    List<SkuStockDTO> getSkuStockBySkuId(@Param("skuIds") List<Long> skuIds);

    /**
     * 查询有库存的仓库Id  如果库存数量不足不会返回skuId
     *
     * @return
     */

    List<WareHasStock> getWareIdHasStock(@Param("skuQuantities") List<SkuQuantityDTO> skuQuantities);


    /**
     * 锁库存
     *
     * @param wareId
     * @param skuId
     * @param quantity
     * @return
     */
    boolean updateStockLock(@Param("wareId") Long wareId, @Param("skuId") Long skuId, @Param("quantity") Integer quantity);


    int updateStockUnlock(@Param("wareOrderTaskDetail") WareOrderTaskDetail wareOrderTaskDetail);


    /**
     * 解锁锁定库存并扣减库存
     * @param wareOrderTaskDetail
     */
    void updateDeductAndUnlockStock(@Param("wareOrderTaskDetail") WareOrderTaskDetail wareOrderTaskDetail);
}
