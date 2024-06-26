package com.ademozalp.elasticsearch.controller;

import com.ademozalp.elasticsearch.dto.FieldAndValueDto;
import com.ademozalp.elasticsearch.dto.FieldAndValueMapDto;
import com.ademozalp.elasticsearch.dto.ProductDto;
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
    public ProductDto createIndex(@RequestBody Product product){
        return productService.createIndex(product);
    }

    @GetMapping("/get-all")
    public List<ProductDto> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/all-indexes")
    public List<ProductDto> getAllProductsFromAllIndexes(){
        return productService.getAllProductsFromAllIndexes();
    }

    @GetMapping("/get-all-data-from-index")
    public List<ProductDto> getAllDataFromIndex(@RequestParam String indexName){
        return productService.getAllDataFromIndex(indexName);
    }

    @GetMapping("/search-by-field-and-value")
    public List<ProductDto> searchProductsByFieldAndValue(@RequestBody FieldAndValueDto fieldAndValueDto){
        return productService.searchProductsByFieldAndValue(fieldAndValueDto);
    }

    @GetMapping("/bool-query-field-and-value")
    public Set<ProductDto> boolQueryFieldAndValue(@RequestBody FieldAndValueMapDto fieldAndValueMapDto){
        return productService.boolQueryFieldAndValue(fieldAndValueMapDto);
    }

    @GetMapping("/auto-suggest")
    public Set<String> autoSuggestItemsByName(@RequestParam String name){
        return productService.autoSuggestItemsByName(name);
    }
}
