package com.gpmall.search.repository;

import com.gpmall.search.entity.ItemDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author jin
 */
public interface ProductRepository extends ElasticsearchRepository<ItemDocument, Integer> {
}
