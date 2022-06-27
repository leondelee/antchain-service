package com.xunta.antchainservice.dao;

import com.xunta.antchainservice.entity.CollectionBuyLockerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CollectionBuyLockerDao {
    CollectionBuyLockerEntity selectOneByTokenId(@Param("tokenid") Long tokenId);

    int delete(@Param("tokenid") Long tokenId);

    int insert(CollectionBuyLockerEntity collectionBuyLockerEntity);

    int update(CollectionBuyLockerEntity collectionBuyLockerEntity);
}
