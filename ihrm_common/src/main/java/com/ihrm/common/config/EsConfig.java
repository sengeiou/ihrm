//package com.ihrm.common.config;
//
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.client.RestClients;
//
//@Configuration
//public class EsConfig {
//    @Bean
//    RestHighLevelClient elasticsearchClient() {
//        ClientConfiguration configuration = ClientConfiguration.builder()
//                .connectedTo("localhost:9200")
//                 .build();
//        RestHighLevelClient client = RestClients.create(configuration).rest();
//
//        return client;
//    }
//
//}
