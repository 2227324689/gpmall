package com.gpmall.search.services;


import com.gpmall.search.ProductSearchService;
import com.gpmall.search.dto.SearchRequest;
import com.gpmall.search.dto.SearchResponse;
import org.apache.dubbo.config.annotation.Service;

/**
 * 商品搜索服务类 对商品搜索接口API进行实现
 * 并对外提供商品搜索服务
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Override
    public SearchResponse search(SearchRequest request) {
        return SearchResponse.ok().data(null);
    }

    @Override
    public SearchResponse fuzzySearch(SearchRequest request) {
        return SearchResponse.ok().data(null);
    }
}
