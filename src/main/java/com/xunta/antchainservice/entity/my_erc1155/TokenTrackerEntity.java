package com.xunta.antchainservice.entity.my_erc1155;

public class TokenTrackerEntity {
    private Long tokenid;
    private Long subtokenid;
    private String creator;
    private String owner;
    private String createtime;
    private String lastupdatetime;
    private String hash;

    public Long getTokenId() {
        return tokenid;
    }

    public void setTokenId(Long t) {
        tokenid = t;
    }

    public Long getSubTokenId() {
        return subtokenid;
    }

    public void setSubTokenId(Long st) {
        subtokenid = st;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String c) {
        creator = c;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String o) {
        owner = o;
    }

    public String getCreateTime() {
        return createtime;
    }

    public void setCreateTime(String ct) {
        createtime = ct;
    }

    public String getLastUpdateTime() {
        return lastupdatetime;
    }

    public void setLastUpdateTime(String lt) {
        lastupdatetime = lt;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hs) {
        hash = hs;
    }

    @Override
    public String toString() {
        return "Token id is: " + tokenid + ", subTokenId is: ," + subtokenid + ", creator is: " + creator + ", owner is: " + owner
                + ", create time is: " + createtime + ", last update time is: " + lastupdatetime + ", hash is: " + hash;
    }
}
