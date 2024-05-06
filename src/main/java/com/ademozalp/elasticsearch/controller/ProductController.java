package com.ademozalp.elasticsearch.controller;

import com.ademozalp.elasticsearch.dto.FieldAndValueDto;
import com.ademozalp.elasticsearch.dto.FieldAndValueMapDto;
import com.ademozalp.elasticsearch.model.Product;
import com.ademozalp.elasticsearch.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createIndex(@RequestBody Product product){
        return productService.createIndex(product);
    }

    @GetMapping("/get-all")
    public Iterable<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/all-indexes")
    public List<Product> getAllProductsFromAllIndexes(){
        return productService.getAllProductsFromAllIndexes();
    }

    @GetMapping("/get-all-data-from-index")
    public List<Product> getAllDataFromIndex(@RequestParam String indexName){
        return productService.getAllDataFromIndex(indexName);
    }

    @GetMapping("/search-by-field-and-value")
    public List<Product> searchProductsByFieldAndValue(@RequestBody FieldAndValueDto fieldAndValueDto){
        return productService.searchProductsByFieldAndValue(fieldAndValueDto);
    }

    @GetMapping("/bool-query-field-and-value")
    public Set<Product> boolQueryFieldAndValue(@RequestBody FieldAndValueMapDto fieldAndValueMapDto){
        return productService.boolQueryFieldAndValue(fieldAndValueMapDto);
    }

    @GetMapping("/auto-suggest")
    public Set<String> autoSuggestItemsByName(@RequestParam String name){
        return productService.autoSuggestItemsByName(name);
    }

}
