package com.patent.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.patent.manage.entity.AllDataView;

public interface AllDataViewService extends IService<AllDataView> {
    IPage<AllDataView> listPage(Integer page, Integer size);
}
