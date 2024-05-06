package com.ademozalp.elasticsearch.service;

import com.ademozalp.elasticsearch.model.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Service
public class JsonDataService {
    private final ObjectMapper objectMapper;

    public JsonDataService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Product> readProductsFromJson(){
        try {
            ClassPathResource resource = new ClassPathResource("data/products.json");
            InputStream inputStream = resource.getInputStream();
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
