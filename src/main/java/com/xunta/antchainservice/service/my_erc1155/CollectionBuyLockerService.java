package com.xunta.antchainservice.service.my_erc1155;

import com.xunta.antchainservice.dao.CollectionBuyLockerDao;
import com.xunta.antchainservice.entity.CollectionBuyLockerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CollectionBuyLockerService {
    @Autowired
    private CollectionBuyLockerDao collectionBuyLockerDao;

    public CollectionBuyLockerEntity selectionOneByTokenId(Long tokenId) {
        return collectionBuyLockerDao.selectOneByTokenId(tokenId);
    }

    public void insert(CollectionBuyLockerEntity collectionBuyLockerEntity) {
        collectionBuyLockerDao.insert(collectionBuyLockerEntity);
    }

    public void delete(Long tokenId) {
        collectionBuyLockerDao.delete(tokenId);
    }

    public void update(CollectionBuyLockerEntity collectionBuyLockerEntity) {
        collectionBuyLockerDao.update(collectionBuyLockerEntity);
    }
}
