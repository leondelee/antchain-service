package com.xunta.antchainservice.dao.my_erc1155;

import com.xunta.antchainservice.entity.my_erc1155.BelongMapperEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BelongMapper {
    BelongMapperEntity selectOneByTokenId(@Param("tokenid") Long tokenId);

    List<BelongMapperEntity> selectNByCreator(@Param(("creator")) String creator);

    List<BelongMapperEntity> selectNByOwner(@Param(("owner")) String owner);

    int insert(BelongMapperEntity belongMapperEntity);

    int delete(Long tokenId);

    int update(BelongMapperEntity belongMapperEntity);

}
