package com.patent.manage.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.patent.manage.entity.*;
import com.patent.manage.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Consumer;

@Slf4j
@RestController
@RequestMapping("/display")
public class DisplayController {

    private static Long counts;

    @Resource
    private AllDataViewService allDataViewService;
    @Resource
    private BiomassService biomassService;
    @Resource
    private HydrogenService hydrogenService;
    @Resource
    private LilonService lilonService;
    @Resource
    private SolarService solarService;
    @Resource
    private WindService windService;

    @Autowired(required = false)
    private RestHighLevelClient client;

    /**
     * 综合查询
     *
     * @param kw
     * @param field
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/all")
    public ApiResponse<IPage<AllDataView>> searchAllDataView(@RequestParam("kw") String kw,
                                                             @RequestParam("field") String field,
                                                             Integer page, Integer size) throws IOException {
        if (page == null || size == null) {
            page = 1;
            size = 10;
        }
        // 计算es分页
        int from = (page - 1) * size;
        // 如果存在es走es查询
        if (client != null) {
            SearchRequest request = new SearchRequest("patent");
            // 配置分页数据
            request.source().from(from).size(size);
            if ("title".equals(field)) {
                request.source().query(QueryBuilders.matchQuery("title", kw));
            }
            if ("public_num".equals(field)) {
                request.source().query(QueryBuilders.matchQuery("publicNum", kw));
            }
            if ("applicant".equals(field)) {
                request.source().query(QueryBuilders.matchQuery("applicant", kw));
            }
            if ("inventor".equals(field)) {
                request.source().query(QueryBuilders.matchQuery("inventor", kw));
            }
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return ApiResponse.success(parseSearchResponseAsIpage(response, page, size), "来自es的数据");
        }
        IPage<AllDataView> allDataViewIPage = new Page<>(page, size);
        LambdaQueryWrapper<AllDataView> allDataViewLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if ("title".equals(field)) {
            allDataViewLambdaQueryWrapper.like(AllDataView::getTitle, kw);
        }
        if ("public_num".equals(field)) {
            allDataViewLambdaQueryWrapper.like(AllDataView::getPublicNum, kw);
        }
        if ("applicant".equals(field)) {
            allDataViewLambdaQueryWrapper.like(AllDataView::getApplicant, kw);
        }
        if ("inventor".equals(field)) {
            allDataViewLambdaQueryWrapper.like(AllDataView::getInventor, kw);
        }
        return ApiResponse.success(allDataViewService.page(allDataViewIPage, allDataViewLambdaQueryWrapper), "来自mp的数据");
    }

    /**
     * 从全部数据中查询
     * 因为此数据库为视图，分页查询效率很低，要自己写分页查询语句
     *
     * @return
     */
    @GetMapping("/all")
    public ApiResponse<IPage<AllDataView>> getAllDataView(Integer page, Integer size) throws IOException {
        if (page == null || size == null) {
            page = 1;
            size = 10;
        }
        int from = (page - 1) * size;
        if (from + size < 10000) {
            // 走es分页
            SearchRequest request = new SearchRequest("patent");
            request.source().query(QueryBuilders.matchAllQuery());
            request.source().from(from).size(size);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            return ApiResponse.success(parseSearchResponseAsIpage(response, page, size), "来自es的数据");
        }
        IPage<AllDataView> allDataViewIPage = allDataViewService.listPage(page, size);
        allDataViewIPage.setTotal(this.counts);
        return ApiResponse.success(allDataViewIPage, "来自mp的数据");
    }

    /**
     * 有些数据来自es, 此方法可以讲es数据解析为ipage对象返回
     *
     * @param response
     * @param page
     * @param size
     * @return
     */
    public IPage<AllDataView> parseSearchResponseAsIpage(SearchResponse response, int page, int size) {
        SearchHits searchHits = response.getHits();
        TotalHits totalHits = searchHits.getTotalHits();
        // 组装ipage
        IPage<AllDataView> iPage = new Page<>();
        iPage.setTotal(totalHits.value);
        iPage.setPages(page);
        iPage.setSize(size);
        ArrayList<AllDataView> dataViews = new ArrayList<>();
        iPage.setRecords(dataViews);
        for (SearchHit hit : searchHits.getHits()) {
            try {
                dataViews.add(JSON.parseObject(hit.getSourceAsString(), AllDataView.class));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return iPage;
    }

    /**
     * 获取五个专利总数
     *
     * @return
     */
    @GetMapping("/count")
    public ApiResponse<Long> getCount() {
        Long count = (long) (biomassService.count() +
                hydrogenService.count() +
                lilonService.count() +
                solarService.count() +
                windService.count());
        if (this.counts == null || this.counts == 0) {
            this.counts = count;
        }
        return ApiResponse.success(this.counts);
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/biomass")
    public ApiResponse<IPage<Biomass>> pageBiomass(Integer page, Integer size) {
        if (page == null || size == null) {
            page = 1;
            size = 10;
        }
        IPage<Biomass> biomassIPage = new Page<>(page, size);
        return ApiResponse.success(biomassService.page(biomassIPage));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/hydrogen")
    public ApiResponse<IPage<Hydrogen>> pageHydrogen(Integer page, Integer size) {
        if (page == null || size == null) {
            page = 1;
            size = 10;
        }
        IPage<Hydrogen> hydrogenIPage = new Page<>(page, size);
        return ApiResponse.success(hydrogenService.page(hydrogenIPage));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/lilon")
    public ApiResponse<IPage<Lilon>> pageLilon(Integer page, Integer size) {
        if (page == null || size == null) {
            page = 1;
            size = 10;
        }
        IPage<Lilon> lilonIPage = new Page<>(page, size);
        return ApiResponse.success(lilonService.page(lilonIPage));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/solar")
    public ApiResponse<IPage<Solar>> pageSolar(Integer page, Integer size) {
        if (page == null || size == null) {
            page = 1;
            size = 10;
        }
        IPage<Solar> solarIPage = new Page<>(page, size);
        return ApiResponse.success(solarService.page(solarIPage));
    }

    /**
     * 分页查询
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/wind")
    public ApiResponse<IPage<Wind>> pageWind(Integer page, Integer size) {
        if (page == null || size == null) {
            page = 1;
            size = 10;
        }
        IPage<Wind> windIPage = new Page<>(page, size);
        return ApiResponse.success(windService.page(windIPage));
    }

}
