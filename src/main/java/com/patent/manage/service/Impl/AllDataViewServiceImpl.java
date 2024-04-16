package com.patent.manage.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patent.manage.entity.AllDataView;
import com.patent.manage.mapper.AllDataViewMapper;
import com.patent.manage.service.AllDataViewService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AllDataViewServiceImpl extends ServiceImpl<AllDataViewMapper, AllDataView> implements AllDataViewService {

    @Resource
    private AllDataViewMapper allDataViewMapper;

    @Override
    public IPage<AllDataView> listPage(Integer page, Integer size) {
        Integer limit = size;
        Integer offset = (page - 1) * size;
        List<AllDataView> allDataViewList = allDataViewMapper.getPage(limit, offset);
        // 组装IPage对象
        IPage<AllDataView> iPage = new Page<>();
        iPage.setRecords(allDataViewList);
        return iPage;
    }
}
