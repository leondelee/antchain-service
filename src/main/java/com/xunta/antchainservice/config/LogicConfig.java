package com.xunta.antchainservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("logic")
public class LogicConfig {
    // YAML配置的logic段

    private String variable;

    public Throwable getVariable() {
        return null;
    }
}
