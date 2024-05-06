package com.ademozalp.elasticsearch.repository;

import com.ademozalp.elasticsearch.model.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository extends ElasticsearchRepository<Product, Long> {
}
