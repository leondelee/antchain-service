package com.xunta.antchainservice.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class DBConfig {
    // 数据库配置

    @Bean
    @ConfigurationProperties(prefix = "mysql")
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }
}
