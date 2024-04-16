package com.patent.manage.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ESConfig {

    @Value("${es.url}")
    private String esurl;

    /**
     * springboot启动时检查是否可以注入
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // 去除末尾 /
        if (esurl.endsWith("/")) {
            esurl = esurl.substring(0, esurl.length() - 1);
        }
        log.info("load esurl: {}", esurl);
        return getClient();
    }

    public RestHighLevelClient getClient(){
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
                HttpHost.create(esurl)
        ));
        boolean ping = false;
        try {
            ping = client.ping(RequestOptions.DEFAULT);
        } catch (Exception e) {
            return null;
        }
        // ping失败返回空
        return ping ? client : null;
    }
}
