package com.patent.manage;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.patent.manage.entity.Biomass;
import com.patent.manage.service.AllDataViewService;
import com.patent.manage.service.BiomassService;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

@SpringBootTest
class PatentManageApplicationTests {

    @Resource
    private BiomassService biomassService;

    @Resource
    private AllDataViewService allDataViewService;

    @Autowired(required = false)
    private RestHighLevelClient client;

    @Test
    void contextLoads() {

    }

    @Test
    void testBiomass() {
        System.out.println(client == null);
    }

    @Test
    void testCount(){
        System.out.println(biomassService.count());
    }

    @Test
    void testView(){
        System.out.println(allDataViewService.count());
    }
}
