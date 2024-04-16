package com.patent.manage;

import com.patent.manage.entity.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class PatentManageApplication {

    @GetMapping("/is_login")
    @ResponseBody
    public ApiResponse isLogin() {
        return ApiResponse.success(500, false, "ERROR");
    }

    public static void main(String[] args) {
        SpringApplication.run(PatentManageApplication.class, args);
        log.info("数据库管理后台系统成功运行...");
    }
}
