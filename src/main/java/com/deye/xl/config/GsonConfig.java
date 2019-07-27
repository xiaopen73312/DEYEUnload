package com.deye.demo.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GsonConfig {

    @Value("${spring.gson.date-format}")
    private String dateFormat;

    ////设置日期时间格式 序列化null   //格式化输出
    @Bean
    public Gson gson() {
        return new GsonBuilder().setDateFormat(dateFormat).serializeNulls().setPrettyPrinting().
                create();
    }


}
