package com.ademozalp.elasticsearch.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;


@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ademozalp.elasticsearch.repository")
@ComponentScan(basePackages = "com.ademozalp.elasticsearch")
public class ESConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.url}")
    private String elasticsearchUrl;

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticsearchUrl)
                .build();
    }
}
