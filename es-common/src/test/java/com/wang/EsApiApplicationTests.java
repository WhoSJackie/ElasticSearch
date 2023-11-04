//package com.wang;
//
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.client.indices.GetIndexRequest;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//
//@SpringBootTest
//class EsApiApplicationTests {
//
//    @Autowired
//    RestHighLevelClient client;
//
//    @Test
//    void createIndices() throws IOException {
//        CreateIndexRequest request = new CreateIndexRequest("wang_index");
//        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
//        System.out.println(response.isAcknowledged());
//    }
//
//    @Test
//    void testIndexExist() throws IOException {
//        GetIndexRequest request = new GetIndexRequest("wang_index");
//        boolean exist = client.indices().exists(request,RequestOptions.DEFAULT);
//        System.out.println(exist);
//    }
//
//
//
//}
