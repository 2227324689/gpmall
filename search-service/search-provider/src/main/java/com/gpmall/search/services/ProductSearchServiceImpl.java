package com.gpmall.search.services;


import com.google.common.collect.Lists;
import com.gpmall.search.ProductSearchService;
import com.gpmall.search.converter.ProductConverter;
import com.gpmall.search.dto.ProductDto;
import com.gpmall.search.dto.SearchRequest;
import com.gpmall.search.dto.SearchResponse;
import com.gpmall.search.entity.ItemDocument;
import com.gpmall.search.repository.ProductRepository;
import com.gpmall.search.utils.ExceptionProcessorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品搜索服务类 对商品搜索接口API进行实现
 * 并对外提供商品搜索服务
 *
 * @author jin
 * @version v1.0.0
 * @Date 2019年8月10日
 */
@Slf4j
@Service
public class ProductSearchServiceImpl implements ProductSearchService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    ProductConverter productConverter;

    @Override
    public SearchResponse search(SearchRequest request) {
        SearchResponse response = new SearchResponse();
        try {
            request.requestCheck();
            // todo 分页
            Iterable<ItemDocument> elasticRes =
                    productRepository.search(QueryBuilders.termsQuery("title", request.getKeyword()));
            ArrayList<ItemDocument> itemDocuments = Lists.newArrayList(elasticRes);
            List<ProductDto> productDtos = productConverter.items2Dto(itemDocuments);
            response.ok(productDtos);
        }catch (Exception e){
            e.printStackTrace();
            log.error("ProductSearchServiceImpl.search Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public SearchResponse fuzzySearch(SearchRequest request) {
        SearchResponse response = new SearchResponse();
        try {
            request.requestCheck();
            // todo 分页
            Iterable<ItemDocument> elasticRes =
                    productRepository.search(QueryBuilders.fuzzyQuery("title", request.getKeyword()));
            ArrayList<ItemDocument> itemDocuments = Lists.newArrayList(elasticRes);
            List<ProductDto> productDtos = productConverter.items2Dto(itemDocuments);
            response.ok(productDtos);
        }catch (Exception e){
            e.printStackTrace();
            log.error("ProductSearchServiceImpl.fuzzySearch Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public SearchResponse hotProductKeyword() {
        return null;
    }
}
