package com.patent.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.patent.manage.entity.AllDataView;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AllDataViewMapper extends BaseMapper<AllDataView> {

    @Select("select *\n" +
            "from patent_biomass\n" +
            "union\n" +
            "select *\n" +
            "from patent_wind\n" +
            "union\n" +
            "select *\n" +
            "from patent_solar\n" +
            "union\n" +
            "select *\n" +
            "from patent_lilon\n" +
            "union\n" +
            "select *\n" +
            "from patent_hydrogen\n" +
            "limit #{limit}\n" +
            "offset #{offset};")
    List<AllDataView> getPage(@Param("limit") Integer limit, @Param("offset") Integer offset);

}
