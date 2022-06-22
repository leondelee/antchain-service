package com.xunta.antchainservice.entity.my_erc1155;

public class BelongMapperEntity {
    private Long tokenid;
    private String creator;
    private String owner;
    private Long remaincount;

    public Long getTokenId() {
        return tokenid;
    }

    public void setTokenId(Long t) {
        tokenid = t;
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

    public Long getRemainCount() {
        return remaincount;
    }

    public void setRemainCount(Long rc) {
        remaincount = rc;
    }

    @Override
    public String toString() {
        return "Token id is: " + tokenid + ", creator is: " + creator + ", owner is: " + owner + ", remain count is: " + remaincount;
    }
}
