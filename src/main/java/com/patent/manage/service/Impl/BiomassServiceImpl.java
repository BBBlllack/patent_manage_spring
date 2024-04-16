package com.patent.manage.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.patent.manage.entity.Biomass;
import com.patent.manage.mapper.BiomassMapper;
import com.patent.manage.service.BiomassService;
import org.springframework.stereotype.Service;

@Service
public class BiomassServiceImpl extends ServiceImpl<BiomassMapper, Biomass> implements BiomassService {
}
