package com.xunta.antchainservice.contracts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.model.CallRestBizParam;
import com.antfinancial.mychain.baas.tool.restclient.model.Method;
import com.antfinancial.mychain.baas.tool.restclient.response.BaseResp;
import com.xunta.antchainservice.bean.contract.*;
import com.xunta.antchainservice.entity.CollectionBuyLockerEntity;
import com.xunta.antchainservice.entity.my_erc1155.TokenBalanceEntity;
import com.xunta.antchainservice.entity.my_erc1155.TokenTrackerEntity;
import com.xunta.antchainservice.service.CollectionBuyLockerService;
import com.xunta.antchainservice.service.my_erc1155.TokenBalanceService;
import com.xunta.antchainservice.service.my_erc1155.TokenTrackerService;
import com.xunta.antchainservice.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyERC1155 {
    private static final String CONTRACT_NAME = "my_erc1155_v3";
    private static final Logger logger = LoggerFactory.getLogger(MyERC1155.class);

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestClientProperties restClientProperties;

    @Autowired
    private TokenTrackerService tokenTrackerService;

    @Autowired
    private TokenBalanceService tokenBalanceService;

    @Autowired
    private CollectionBuyLockerService collectionBuyLockerService;

    private BaseResp _chainCall(String modelSignature, String inputList, String outputTypes) throws Exception {
        String orderId = "order_" + System.currentTimeMillis();

        CallRestBizParam callRestBizParam=CallRestBizParam.builder()
                .orderId(orderId)
                .bizid(restClientProperties.getBizid())
                .account(restClientProperties.getDefaultAccount())
                .contractName(CONTRACT_NAME)
                .methodSignature(modelSignature)
                .inputParamListStr(inputList)
                .outTypes(outputTypes)
                .mykmsKeyId(restClientProperties.getKmsId())
                .method(Method.CALLCONTRACTBIZ)
                .tenantid(restClientProperties.getTenantid())
                .gas(1000000L).build();
        return restClient.chainCallForBiz(callRestBizParam);
    }

    public BatchListResponse getAllBatches() {
        BatchListResponse rsp = new BatchListResponse();
        rsp.resultList = tokenBalanceService.selectAll();
        return rsp;
    }

    public BatchInfoResponse getBatchInfo(Long tokenId) {
        BatchInfoResponse rsp = new BatchInfoResponse();
        TokenBalanceEntity tokenBalanceEntity = tokenBalanceService.selectOneByTokenId(tokenId);
        if(tokenBalanceEntity == null) {
            return rsp;
        }
        rsp.tokenId = tokenId;
        rsp.tokenUri = tokenBalanceEntity.getTokenuri();
        rsp.creator = tokenBalanceEntity.getCreator();
        rsp.createTime = tokenBalanceEntity.getCreateTime();
        rsp.remainCount = tokenBalanceEntity.getRemainCount();
        rsp.title = tokenBalanceEntity.getTitle();
        rsp.description = tokenBalanceEntity.getDescription();
        rsp.price = tokenBalanceEntity.getPrice();
        return rsp;
    }

    public TokenInfoResponse getTokenInfo(Long tokenId, Long subTokenId) {
        TokenInfoResponse rsp = new TokenInfoResponse();
        rsp.tokenId = tokenId;
        rsp.subTokenId = subTokenId;
        TokenTrackerEntity tokenTrackerEntity = tokenTrackerService.selectOneBySubTokenId(tokenId, subTokenId);
        TokenBalanceEntity tokenBalanceEntity = tokenBalanceService.selectOneByTokenId(tokenId);
        if(tokenTrackerEntity == null || tokenBalanceEntity == null) {
            return rsp;
        }
        rsp.tokenUri = tokenBalanceEntity.getTokenuri();
        rsp.creator = tokenTrackerEntity.getCreator();
        rsp.createTime = tokenTrackerEntity.getCreateTime();
        rsp.owner = tokenTrackerEntity.getOwner();
        rsp.lastUpdateTime = tokenTrackerEntity.getLastUpdateTime();
        rsp.hash = tokenTrackerEntity.getHash();
        rsp.title = tokenBalanceEntity.getTitle();
        rsp.description = tokenBalanceEntity.getDescription();
        rsp.price = tokenBalanceEntity.getPrice();
        return rsp;
    }

    public Long getBatchCount() {
        List<TokenBalanceEntity> tokens = tokenBalanceService.selectAll();
        return (long) tokens.size();
    }

    public Long getMaxSubTokenId(Long tokenId) {
        Long id =  tokenTrackerService.selectMaxSubTokenId(tokenId);
        if(id != null && id >= 0) return id;
        else return -1L;
    }

    public Long getTokenMintCount(Long tokenId) {
        TokenBalanceEntity tokenBalanceEntity = tokenBalanceService.selectOneByTokenId(tokenId);
        return tokenBalanceEntity == null ? 0L : tokenBalanceEntity.getMintCount();
    }

    public Long getTokenRemainCount(Long tokenId) {
        TokenBalanceEntity tokenBalanceEntity = tokenBalanceService.selectOneByTokenId(tokenId);
        return tokenBalanceEntity == null ? 0L : tokenBalanceEntity.getRemainCount();
    }

    public String getTokenCreator(Long tokenId) {
        TokenBalanceEntity tokenBalanceEntity = tokenBalanceService.selectOneByTokenId(tokenId);
        return tokenBalanceEntity == null ? "" : tokenBalanceEntity.getCreator();
    }

    public String getSubTokenOwner(Long tokenId, Long subTokenId) {
        TokenTrackerEntity tokenTrackerEntity = tokenTrackerService.selectOneBySubTokenId(tokenId, subTokenId);
        return tokenTrackerEntity == null ? "" : tokenTrackerEntity.getOwner();
    }

    public String getTokenCreateTime(Long tokenId) {
        TokenBalanceEntity tokenBalanceEntity = tokenBalanceService.selectOneByTokenId(tokenId);
        return tokenBalanceEntity == null ? "" : tokenBalanceEntity.getCreateTime();
    }

    public String getSubTokenLastUpdateTime(Long tokenId, Long subTokenId) {
        TokenTrackerEntity tokenTrackerEntity = tokenTrackerService.selectOneBySubTokenId(tokenId, subTokenId);
        return tokenTrackerEntity == null ? "" : tokenTrackerEntity.getLastUpdateTime();
    }

    public String getTokenUriFromChain(Long tokenId) {
        String modelSignature = "getTokenUri(uint256)";
        JSONArray inputList = new JSONArray();
        JSONArray outTypes = new JSONArray();
        inputList.add(BigInteger.valueOf(tokenId));
        outTypes.add("string");
        BaseResp rsp = null;
        String res = "";
        try {
            rsp = _chainCall(modelSignature, JSON.toJSONString(inputList), JSON.toJSONString(outTypes));
            if(rsp != null) {
                String dataStr = rsp.getData();
                if(! dataStr.equals("")) {
                    res = JSON.parseObject(dataStr).getJSONArray("outRes").getString(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error batchMint: {}", e.toString());
            return res;
        }
        return res;
    }

    // transaction
    public ContractTxResponse batchMint(BatchMintRequest batchMintRequest) {
        String _uri = batchMintRequest.uri;
        Long _count = batchMintRequest.count;
        String  _from = batchMintRequest.from;
        String title = batchMintRequest.title;
        String description = batchMintRequest.description;
        double price = batchMintRequest.price;
        ContractTxResponse msgRsp = new ContractTxResponse();
        String modelSignature = "batchMint(string,uint256)";
        JSONArray inputList = new JSONArray();
        JSONArray outTypes = new JSONArray();
        inputList.add(_uri);
        inputList.add(BigInteger.valueOf(_count));
        outTypes.add("uint256");
        BaseResp rsp = null;
        try {
            rsp = _chainCall(modelSignature, JSON.toJSONString(inputList), JSON.toJSONString(outTypes));
            if(rsp != null) {
                String dataStr = rsp.getData();
                if(! dataStr.equals("")) {
                    long tokenIdx = JSON.parseObject(dataStr).getJSONArray("outRes").getBigInteger(0).longValue();
                    // add token balance db
                    TokenBalanceEntity tokenBalanceEntity = new TokenBalanceEntity();
                    tokenBalanceEntity.setTokenId(tokenIdx);
                    tokenBalanceEntity.setMintCount(_count);
                    tokenBalanceEntity.setRemainCount(_count);
                    tokenBalanceEntity.setCreator(_from);
                    tokenBalanceEntity.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
                    tokenBalanceEntity.setTitle(title);
                    tokenBalanceEntity.setDescription(description);
                    tokenBalanceEntity.setPrice(price);
                    tokenBalanceEntity.setTokenuri(_uri);
                    tokenBalanceService.insert(tokenBalanceEntity);

                    // add buy locker db
                    CollectionBuyLockerEntity collectionBuyLockerEntity = new CollectionBuyLockerEntity();
                    collectionBuyLockerEntity.tokenid = tokenIdx;
                    collectionBuyLockerEntity.locknumber = _count;
                    collectionBuyLockerService.insert(collectionBuyLockerEntity);
                }
                msgRsp.success = true;
            } else {
                msgRsp.success = false;
                msgRsp.message = "Block chain response is null!";
            }
            return msgRsp;
        } catch (Exception e) {
            e.printStackTrace();
            msgRsp.success =false;
            msgRsp.message = e.toString();
            return msgRsp;
        }
    }

    public ContractTxResponse transfer(Long tokenId, Long subTokenId, String from, String to) {
        ContractTxResponse rsp = new ContractTxResponse();
        TokenBalanceEntity tokenBalanceEntity = tokenBalanceService.selectOneByTokenId(tokenId);
        TokenTrackerEntity tokenTrackerEntity = tokenTrackerService.selectOneBySubTokenId(tokenId, subTokenId);
        if(tokenBalanceEntity == null) {
            rsp.success = false;
            rsp.message = "Token id: " + tokenId + ", subTokenId: " + subTokenId + " not found!";
            return rsp;
        }
        else if(tokenTrackerEntity == null) {
            String creator = tokenBalanceEntity.getCreator();
            Long remainCount = tokenBalanceEntity.getRemainCount();
            if(remainCount == 0) {
                rsp.success = false;
                rsp.message = "Sold out!";
                return rsp;
            }
            else if(! creator.equals(from)) {
                rsp.success = false;
                rsp.message = "From is not owner!";
                return rsp;
            }
            else {
                tokenBalanceEntity.setRemainCount(remainCount - 1);
                tokenTrackerEntity = new TokenTrackerEntity();
                tokenTrackerEntity.setOwner(to);
                tokenTrackerEntity.setCreator(creator);
                tokenTrackerEntity.setTokenId(tokenId);
                tokenTrackerEntity.setSubTokenId(subTokenId);
                tokenTrackerEntity.setCreateTime(tokenBalanceEntity.getCreateTime());
                tokenTrackerEntity.setLastUpdateTime(String.valueOf(System.currentTimeMillis() / 1000));
                tokenTrackerEntity.setHash(HashUtils.generateSHA256String(tokenId + "_" + subTokenId + "_hash"));
                tokenBalanceService.update(tokenBalanceEntity);
                tokenTrackerService.insert(tokenTrackerEntity);
                rsp.success = true;
                return rsp;
            }
        }
        else {
            String owner = tokenTrackerEntity.getOwner();
            if(! owner.equals(from)) {
                rsp.success = false;
                rsp.message = "From is not owner!";
                return rsp;
            }
            else {
                tokenTrackerEntity.setOwner(to);
                tokenTrackerEntity.setLastUpdateTime(String.valueOf(System.currentTimeMillis() / 1000));
                tokenTrackerService.update(tokenTrackerEntity);
                rsp.success = true;
                return rsp;
            }
        }
    }

    public ContractTxResponse burn(Long tokenId, Long subTokenId, String from) {
        // only tokens that are sold can be burned
        ContractTxResponse rsp = new ContractTxResponse();
        rsp.success = true;
        TokenTrackerEntity tokenTrackerEntity = tokenTrackerService.selectOneBySubTokenId(tokenId, subTokenId);
        if(tokenTrackerEntity == null) {
            rsp.success = false;
            rsp.message = "Token is not yet sold";
        }
        else {
            String owner = tokenTrackerEntity.getOwner();
            if(!owner.equals(from)) {
                rsp.success = false;
                rsp.message = "From is not owner!";
            }
            else {
                tokenTrackerService.deleteOneBySubTokenId(tokenId, subTokenId);
            }
        }
        return rsp;
    }
}
