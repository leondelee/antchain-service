package com.xunta.antchainservice.service.my_erc1155;

import com.xunta.antchainservice.dao.my_erc1155.TokenTrackerDao;
import com.xunta.antchainservice.entity.my_erc1155.TokenTrackerEntity;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenTrackerService {
    @Autowired
    TokenTrackerDao tokenTrackerDao;

    public List<TokenTrackerEntity> selectNByTokenId(Long tokenId) {
        return tokenTrackerDao.selectNByTokenId(tokenId);
    }

    public Long selectMaxSubTokenId(Long tokenId) {
        return tokenTrackerDao.selectMaxSubTokenId(tokenId);
    }

    public TokenTrackerEntity selectOneBySubTokenId(Long tokenId, Long subTokenId) {
        return tokenTrackerDao.selectOneBySubTokenId(tokenId, subTokenId);
    }

    public TokenTrackerEntity selectOneByHash(String hash) {
        return tokenTrackerDao.selectOneByHash(hash);
    }

    public List<TokenTrackerEntity> selectNByCreator(String creator) {
        return tokenTrackerDao.selectNByCreator(creator);
    }

    public List<TokenTrackerEntity> selectNByOwner(String owner) {
        return tokenTrackerDao.selectNByOwner(owner);
    }

    public void insert(TokenTrackerEntity tokenTrackerEntity) {
        tokenTrackerDao.insert(tokenTrackerEntity);
    }

    public void deleteAllByTokenId(Long tokenId) {
        tokenTrackerDao.deleteAllByTokenId(tokenId);
    }
    public void deleteOneBySubTokenId(Long tokenId, Long subTokenId) {
        tokenTrackerDao.deleteOneBySubTokenId(tokenId, subTokenId);
    }

    public void update(TokenTrackerEntity belongMapperEntity) {
        tokenTrackerDao.update(belongMapperEntity);
    }
}
