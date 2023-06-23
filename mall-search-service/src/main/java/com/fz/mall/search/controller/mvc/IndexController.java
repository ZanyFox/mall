package com.fz.mall.search.controller.mvc;

import com.fz.mall.search.pojo.dto.SearchParamDTO;
import com.fz.mall.search.pojo.vo.SearchResultVO;
import com.fz.mall.search.service.impl.MallSearchService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {


    @Autowired
    private MallSearchService mallSearchService;

    @RequestMapping({"/", "/index", "/index.html"})
    public String index(SearchParamDTO searchParamDTO, Model model) {
        if (ObjectUtils.isNotEmpty(searchParamDTO.getKeyword())) {
            SearchResultVO searchResult = mallSearchService.search(searchParamDTO);
            model.addAttribute("result", searchResult);
        }
        return "index";
    }

    @RequestMapping("/search")
    public String search(SearchParamDTO searchParamDTO, Model model) {

        SearchResultVO searchResult = mallSearchService.search(searchParamDTO);
        model.addAttribute("result", searchResult);
        return "index";
    }
}
