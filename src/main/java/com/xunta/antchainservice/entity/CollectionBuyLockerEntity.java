package com.xunta.antchainservice.entity;

public class CollectionBuyLockerEntity {
    public Long tokenid;
    public Long locknumber;

    public Long getTokenId() {
        return tokenid;
    }

    public void setTokenId(Long t) {
        tokenid = t;
    }

    public long getLockNumber() {
        return locknumber;
    }

    public void setLockNumber(long i) {
        locknumber = i;
    }
}
