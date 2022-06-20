package com.xunta.antchainservice.entity;

import lombok.Data;

@Data
public class User {
    // 数据库对象

    private Long id;
    private String username;

    public String getUsername() {
        return username;
    }
}