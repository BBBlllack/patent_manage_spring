package com.patent.manage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patent.manage.entity.Hydrogen;
import com.patent.manage.mapper.HydrogenMapper;
import com.patent.manage.service.HydrogenService;
import org.springframework.stereotype.Service;

@Service
public class HydrogenServiceImpl extends ServiceImpl<HydrogenMapper, Hydrogen> implements HydrogenService {
}
