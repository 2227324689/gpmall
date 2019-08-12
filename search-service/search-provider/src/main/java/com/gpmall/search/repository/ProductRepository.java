package com.gpmall.search.repository;

import com.gpmall.search.entity.ProductSearchModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author jin
 */
@Component
public interface ProductRepository extends ElasticsearchRepository<ProductSearchModel, Integer> {
}
