package com.ademozalp.elasticsearch.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@Document(indexName = "products_index")
@Setting(settingPath = "static/es-settings.json")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    @Id
    private Long id;

    @Field(name = "name", type = FieldType.Text, analyzer = "custom_index", searchAnalyzer = "custom_search")
    private String name;

    @Field(name = "price", type = FieldType.Long)
    private Long price;

    @Field(name = "description", type = FieldType.Text, analyzer = "custom_index", searchAnalyzer = "custom_search")
    private String description;

    @Field(name = "point", type = FieldType.Integer)
    private Integer point;
}
