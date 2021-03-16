package com.example.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * @author 周杨清
 * @date 2021/3/3 12:10
 */

/**
 * 导入依赖
 * 编写配置: 给容器中注入一个RestHighLevelClient
 *
 */
@Configurable
public class ElasticSearchConfig {
    public  static final RequestOptions COMMON_OPTIONS;
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        COMMON_OPTIONS = builder.build();
    }

    @Bean
    public RestHighLevelClient esRestClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("1.15.138.161", 9200, "http")));
        return client;
    }

}
