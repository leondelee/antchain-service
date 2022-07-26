package com.xunta.antchainservice.entity.my_erc1155;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter

public class TokenBalanceEntity {
    private Long tokenid;
    private Long mintcount;
    private Long remaincount;
    private String creator;
    private String createtime;
    private String hash;
    private String title;
    private String description;
    private double price;
    private String tokenuri;
    private String tags;
    private double score;

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

    public String getHash() {
        return hash;
    }

    public void setHash(String hs) {
        hash = hs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String hs) {
        title = hs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String hs) {
        description = hs;
    }


    @Override
    public String toString() {
        return "Token id is: " + tokenid + ", remain count is: ," + remaincount + ", creator  is: " + creator + ", create time is: "
                + ", hash is: " + hash;
    }
}
