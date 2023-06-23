package com.fz.mall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticConfig {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
       return new RestHighLevelClient(RestClient.builder(HttpHost.create("http://192.168.214.133:9200")));
    }
}
