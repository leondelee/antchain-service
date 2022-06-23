package com.xunta.antchainservice.dao.my_erc1155;

import com.xunta.antchainservice.entity.my_erc1155.TokenBalanceEntity;
import com.xunta.antchainservice.entity.my_erc1155.TokenTrackerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TokenBalanceDao {
    TokenBalanceEntity selectOneByTokenId(@Param("tokenid") Long tokenId);

    List<TokenBalanceEntity> selectAll();

    List<TokenBalanceEntity> selectNByCreator(@Param("creator") String creator);

    int insert( TokenBalanceEntity tokenBalanceEntity);

    int delete(@Param("tokenid") Long tokenId);

    int update(TokenBalanceEntity tokenBalanceEntity);

}