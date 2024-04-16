package com.patent.manage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patent.manage.entity.Solar;
import com.patent.manage.mapper.SolarMapper;
import com.patent.manage.service.SolarService;
import org.springframework.stereotype.Service;

@Service
public class SolarServiceImpl extends ServiceImpl<SolarMapper, Solar> implements SolarService {
}
