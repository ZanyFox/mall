package com.fz.mall.goods.service;

import com.fz.mall.common.data.bo.EsSkuBO;
import com.fz.mall.common.pojo.vo.PageVO;
import com.fz.mall.goods.pojo.dto.SpuPageDTO;
import com.fz.mall.goods.pojo.dto.SpuSaveDTO;
import com.fz.mall.goods.pojo.dto.UpdateSpuStatusDTO;
import com.fz.mall.goods.pojo.entity.SpuInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu信息 服务类
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface SpuInfoService extends IService<SpuInfo> {

    void save(SpuSaveDTO spuSave);


    PageVO<SpuInfo> list(SpuPageDTO spuPageDTO);

    /**
     * 更新spu状态
     */
    void updateSpuStatus(UpdateSpuStatusDTO updateSpuStatusDTO);

    /**
     * 根据spuId构造搜索业务对象
     * @param spuId
     * @return
     */
    List<EsSkuBO> getEsSkuBOsBySpuId(Long spuId);
}
