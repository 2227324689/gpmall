package com.gpmall.search;

import com.gpmall.search.dto.SearchRequest;
import com.gpmall.search.dto.SearchResponse;

/**
 * 商城全部商品搜索API推荐放在此接口中
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
public interface ProductSearchService {
    /**
     * 搜索商品 精准搜索
     *
     * @param request
     * @return
     */
    SearchResponse search(SearchRequest request);

    /**
     * 搜索商品 模糊搜索
     *
     * @param request
     * @return
     */
    SearchResponse fuzzySearch(SearchRequest request);
}
