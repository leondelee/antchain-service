package com.xunta.antchainservice.service.my_erc1155;

import com.xunta.antchainservice.dao.my_erc1155.BelongMapper;
import com.xunta.antchainservice.entity.my_erc1155.BelongMapperEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BelongMapperService {
    @Autowired
    BelongMapper belongMapper;

    public BelongMapperEntity getBelongMapperByTokenId(Long tokenId) {
        return belongMapper.selectOneByTokenId(tokenId);
    }

    public List<BelongMapperEntity> getBelongMappersByCreator(String creator) {
        return belongMapper.selectNByCreator(creator);
    }

    public List<BelongMapperEntity> getBelongMappersByOwner(String owner) {
        return belongMapper.selectNByOwner(owner);
    }

    public void insert(BelongMapperEntity belongMapperEntity) {
        belongMapper.insert(belongMapperEntity);
    }

    public void delete(Long tokenId) {
        belongMapper.delete(tokenId);
    }

    public void update(BelongMapperEntity belongMapperEntity) {
        belongMapper.update(belongMapperEntity);
    }
}
