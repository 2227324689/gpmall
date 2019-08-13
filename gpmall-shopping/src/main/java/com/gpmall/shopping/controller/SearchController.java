package com.gpmall.shopping.controller;

import com.gpmall.commons.result.ResponseData;
import com.gpmall.commons.result.ResponseUtil;
import com.gpmall.search.ProductSearchService;
import com.gpmall.search.dto.SearchRequest;
import com.gpmall.search.dto.SearchResponse;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商城全部商品搜索和热门推荐
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月11日
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Reference(timeout = 30000)
    private ProductSearchService productSearchService;

    @GetMapping("/product/{keyword}")
    public ResponseData<SearchResponse> searchProduct(@PathVariable("keyword") String keyword) {
        SearchResponse searchResponse = productSearchService.search(SearchRequest.of(keyword));
        return new ResponseUtil().setData(searchResponse.getData());
    }
}
