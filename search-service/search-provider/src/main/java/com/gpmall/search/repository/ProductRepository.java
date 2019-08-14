package com.gpmall.search.repository;

import com.gpmall.search.entity.ProductSearchModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author jin
 */
public interface ProductRepository extends ElasticsearchRepository<ProductSearchModel, Integer> {
}
