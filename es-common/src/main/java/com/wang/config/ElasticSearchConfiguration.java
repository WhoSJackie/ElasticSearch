//package com.wang.config;
//
//import org.apache.http.HttpHost;
//import org.apache.http.client.config.RequestConfig;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ElasticSearchConfiguration {
//
//    @Bean
//    public RestHighLevelClient getRestHighLevelClient(){
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("localhost", 9200, "http"),
//                        new HttpHost("localhost", 9201, "http")
//                        ).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
//                    @Override
//                    public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
//                        return builder.setConnectTimeout(5000*1000).setSocketTimeout(6000*1000);
//                    }
//                })
//        );
//        return client;
//    }
//
//
//}
