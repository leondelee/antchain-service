package com.xunta.antchainservice.service.my_erc1155;

import com.xunta.antchainservice.dao.my_erc1155.TokenBalanceDao;
import com.xunta.antchainservice.entity.my_erc1155.TokenBalanceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenBalanceService {
    @Autowired
    TokenBalanceDao tokenBalanceDao;

    public TokenBalanceEntity selectOneByTokenId(Long tokenId) {
        return tokenBalanceDao.selectOneByTokenId(tokenId);
    }

    public List<TokenBalanceEntity> selectAll() {
        return tokenBalanceDao.selectAll();
    }

    public List<TokenBalanceEntity> selectNByCreator(String creator) {
        return tokenBalanceDao.selectNByCreator(creator);
    }

    public void insert(TokenBalanceEntity tokenBalanceEntity) {
        tokenBalanceDao.insert(tokenBalanceEntity);
    }

    public void delete(Long tokenId) {
        tokenBalanceDao.delete(tokenId);
    }

    public void update(TokenBalanceEntity tokenBalanceEntity) {
        tokenBalanceDao.update(tokenBalanceEntity);
    }
}
