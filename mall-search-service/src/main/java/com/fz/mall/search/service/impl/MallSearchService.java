package com.fz.mall.search.service.impl;

import com.fz.mall.search.pojo.dto.SearchParamDTO;
import com.fz.mall.search.pojo.vo.SearchResultVO;

public interface MallSearchService {

    SearchResultVO search(SearchParamDTO searchParamDTO);
}
