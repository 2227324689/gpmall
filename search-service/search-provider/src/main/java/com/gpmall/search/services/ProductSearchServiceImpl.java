package com.gpmall.search.services;


import com.gpmall.search.ProductSearchService;
import com.gpmall.search.dto.SearchRequest;
import com.gpmall.search.dto.SearchResponse;
import com.gpmall.search.entity.ProductSearchModel;
import com.gpmall.search.repository.ProductRepository;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

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

    @Autowired
    private ProductRepository productRepository;

    @Override
    public SearchResponse search(SearchRequest request) {
        request.requestCheck();
        Iterable<ProductSearchModel> elasticRes =
                productRepository.search(QueryBuilders.termsQuery(request.getKeyword(), "title", "sell_point"));
        ArrayList<ProductSearchModel> response = new ArrayList<>();
        elasticRes.forEach(response::add);
        return SearchResponse.ok().data(response);
    }

    @Override
    public SearchResponse fuzzySearch(SearchRequest request) {
        request.requestCheck();
        Iterable<ProductSearchModel> elasticRes =
                productRepository.search(QueryBuilders.fuzzyQuery("title", request.getKeyword()));
        ArrayList<ProductSearchModel> response = new ArrayList<>();
		elasticRes.forEach(response::add);
        return SearchResponse.ok().data(response);
    }

    @Override
    public SearchResponse hotProductKeyword() {
        return null;
    }
}
