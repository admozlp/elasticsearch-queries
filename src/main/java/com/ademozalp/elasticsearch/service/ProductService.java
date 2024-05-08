package com.ademozalp.elasticsearch.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.ademozalp.elasticsearch.dto.FieldAndValueDto;
import com.ademozalp.elasticsearch.dto.FieldAndValueMapDto;
import com.ademozalp.elasticsearch.dto.ProductDto;
import com.ademozalp.elasticsearch.model.Product;
import com.ademozalp.elasticsearch.repository.ProductRepository;
import com.ademozalp.elasticsearch.util.ESUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final JsonDataService jsonDataService;
    private final ElasticsearchClient elasticsearchClient;

    public ProductService(ProductRepository productRepository, JsonDataService jsonDataService, ElasticsearchClient elasticsearchClient) {
        this.productRepository = productRepository;
        this.jsonDataService = jsonDataService;
        this.elasticsearchClient = elasticsearchClient;
    }

    @PostConstruct
    public void addProductsFromJson(){
        List<Product> products = jsonDataService.readProductsFromJson();
        productRepository.saveAll(products);
    }

    public ProductDto createIndex(Product product){
        Product savedProduct = productRepository.save(product);
        return new ProductDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(),
                savedProduct.getDescription(), savedProduct.getPoint());
    }

    public List<ProductDto> getAllProducts(){
        List<ProductDto> products = new ArrayList<>();

        productRepository.findAll().forEach(product ->
                products.add(new ProductDto(product.getId(), product.getName(), product.getPrice(),
                    product.getDescription(), product.getPoint()))
        );

        return products;
    }

    public List<ProductDto> getAllProductsFromAllIndexes(){
        try {
            Query query = ESUtil.createMatchAllQuery();

            SearchResponse<Product> response = elasticsearchClient.search(q -> q.query(query), Product.class);

            List<Product> products = extractProductsFromResponse(response);

            return products.stream().map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice(),
                    product.getDescription(), product.getPoint())).toList();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public List<ProductDto> getAllDataFromIndex(String indexName){
        try {
            Query query = ESUtil.createMatchAllQuery();
            SearchResponse<Product> response = elasticsearchClient
                    .search(q -> q.index(indexName).query(query), Product.class);

            List<Product> products = extractProductsFromResponse(response);

            return products.stream().map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice(),
                    product.getDescription(), product.getPoint())).toList();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public List<ProductDto> searchProductsByFieldAndValue(FieldAndValueDto fieldAndValueDto){
        try {
            Supplier<Query> querySupplier = ESUtil.buildQueryForFieldAndValue(fieldAndValueDto.field(), fieldAndValueDto.value());
            SearchResponse<Product> response =
                    elasticsearchClient.search(q -> q.index("products_index").query(querySupplier.get()), Product.class);

            List<Product> products = extractProductsFromResponse(response);

            return products.stream().map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice(),
                    product.getDescription(), product.getPoint())).toList();
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public Set<ProductDto> boolQueryFieldAndValue(FieldAndValueMapDto fieldAndValueMapDto){
        try {
            Set<Product> products = new HashSet<>();

            for (Map.Entry<String, String> entry : fieldAndValueMapDto.fieldAndValueMap().entrySet()){
                Supplier<Query> querySupplier = ESUtil.createBoolQuery(entry.getKey(), entry.getValue());

                SearchResponse<Product> response =
                        elasticsearchClient.search(q -> q.index("products_index").query(querySupplier.get()), Product.class);

                products.addAll(extractProductsFromResponse(response));
            }

            return products.stream().map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice(),
                    product.getDescription(), product.getPoint())).collect(Collectors.toSet());

        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public Set<String> autoSuggestItemsByName(String name){
        try {
            Query query = ESUtil.buildAutoSuggestQuery(name);
            SearchResponse<Product> search =
                    elasticsearchClient.search(q -> q.index("products_index").query(query), Product.class);

            return search.hits()
                    .hits()
                    .stream()
                    .map(Hit::source).filter(Objects::nonNull)
                    .map(Product::getName)
                    .collect(Collectors.toSet());
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    private List<Product> extractProductsFromResponse(SearchResponse<Product> response) {
        return response
                .hits()
                .hits()
                .stream()
                .map(Hit::source)
                .collect(Collectors.toList());
    }
}
