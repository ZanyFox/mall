package com.fz.mall.search.controller.app;

import com.fz.mall.common.resp.ServRespEntity;
import com.fz.mall.search.pojo.dto.SearchParamDTO;
import com.fz.mall.search.pojo.vo.SearchResultVO;
import com.fz.mall.search.service.impl.MallSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/search")
public class SearchController {

    @Autowired
    private MallSearchService mallSearchService;

    @GetMapping
    public ServRespEntity search(SearchParamDTO searchParamDTO) {

        SearchResultVO search = mallSearchService.search(searchParamDTO);
        return ServRespEntity.success(search);
    }
}
