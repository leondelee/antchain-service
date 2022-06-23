package com.xunta.antchainservice.entity.my_erc1155;

public class TokenBalanceEntity {
    private Long tokenid;
    private Long mintcount;
    private Long remaincount;
    private String creator;
    private String createtime;

    public Long getTokenId() {
        return tokenid;
    }

    public void setTokenId(Long t) {
        tokenid = t;
    }

    public Long getMintCount() {
        return mintcount;
    }

    public void setMintCount(Long t) {
        mintcount = t;
    }

    public Long getRemainCount() {
        return remaincount;
    }

    public void setRemainCount(Long t) {
        remaincount = t;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String sc) {
        creator = sc;
    }

    public String getCreateTime() {
        return createtime;
    }

    public void setCreateTime(String ct) {
        createtime = ct;
    }


    @Override
    public String toString() {
        return "Token id is: " + tokenid + ", remain count is: ," + remaincount + ", creator  is: " + creator + ", create time is: " + createtime;
    }
}
