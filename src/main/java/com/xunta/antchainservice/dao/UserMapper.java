package com.xunta.antchainservice.dao;

import com.xunta.antchainservice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    //更多方案参考
    //https://blog.csdn.net/iiiliuyang/article/details/104162463

    User selectOneByUsername(@Param("username") String username);

    User selectOneByUserId(@Param("id") Long userId);
}
