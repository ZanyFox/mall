package com.fz.mall.goods.mapper;

import com.fz.mall.goods.pojo.dto.UpdateSpuStatusDTO;
import com.fz.mall.goods.pojo.entity.SpuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * <p>
 * spu信息 Mapper 接口
 * </p>
 *
 * @author Fan
 * @since 2022-11-12 14:17:44
 */
public interface SpuInfoMapper extends BaseMapper<SpuInfo> {

    void updateSpuStatus(UpdateSpuStatusDTO updateSpuStatusDTO);

}
