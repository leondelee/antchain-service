package com.xunta.antchainservice.dao.my_erc1155;

import com.xunta.antchainservice.entity.my_erc1155.TokenTrackerEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TokenTrackerDao {
    List<TokenTrackerEntity> selectNByTokenId(@Param("tokenid") Long tokenId);

    Long selectMaxSubTokenId(@Param("tokenid") Long tokenId);

    TokenTrackerEntity selectOneBySubTokenId(@Param("tokenid") Long tokenId, @Param("subtokenid") Long subTokenId);

    TokenTrackerEntity selectOneByHash(@Param("hash") String hash);

    List<TokenTrackerEntity> selectNByCreator(@Param("creator") String creator);

    List<TokenTrackerEntity> selectNByOwner(@Param(("owner")) String owner);

    int insert( TokenTrackerEntity tokenTrackerEntity);

    int deleteAllByTokenId(@Param("tokenid") Long tokenId);
    int deleteOneBySubTokenId(@Param("tokenid") Long tokenId, @Param("subtokenid") Long subTokenId);

    int update(TokenTrackerEntity belongMapperEntity);

}
