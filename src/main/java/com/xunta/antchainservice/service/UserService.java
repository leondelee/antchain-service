package com.xunta.antchainservice.service;

import com.xunta.antchainservice.dao.UserMapper;
import com.xunta.antchainservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    // 用户服务

    @Autowired
    private UserMapper userMapper;

    public User getUserByName(String name) {
        return userMapper.selectOneByUsername(name);
    }

    public User getUserByUserId(Long userId) {
        return userMapper.selectOneByUserId(userId);
    }

}
