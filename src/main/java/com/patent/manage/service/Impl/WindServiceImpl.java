package com.patent.manage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patent.manage.entity.Wind;
import com.patent.manage.mapper.WindMapper;
import com.patent.manage.service.WindService;
import org.springframework.stereotype.Service;

@Service
public class WindServiceImpl extends ServiceImpl<WindMapper, Wind> implements WindService {
}
