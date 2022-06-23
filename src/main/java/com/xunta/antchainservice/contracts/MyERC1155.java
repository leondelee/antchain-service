package com.xunta.antchainservice.contracts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.model.CallRestBizParam;
import com.antfinancial.mychain.baas.tool.restclient.model.Method;
import com.antfinancial.mychain.baas.tool.restclient.response.BaseResp;
import com.xunta.antchainservice.bean.contract.ContractTxResponse;
import com.xunta.antchainservice.entity.my_erc1155.TokenBalanceEntity;
import com.xunta.antchainservice.entity.my_erc1155.TokenTrackerEntity;
import com.xunta.antchainservice.service.my_erc1155.TokenBalanceService;
import com.xunta.antchainservice.service.my_erc1155.TokenTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

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

    public Long getBatchCount() {
        List<TokenBalanceEntity> tokens = tokenBalanceService.selectAll();
        return (long) tokens.size();
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

    public String getTokenUri(Long tokenId) {
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
    public ContractTxResponse batchMint(String _uri, Long _count, String _from) {
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
                    TokenBalanceEntity tokenBalanceEntity = new TokenBalanceEntity();
                    tokenBalanceEntity.setTokenId(tokenIdx);
                    tokenBalanceEntity.setMintCount(_count);
                    tokenBalanceEntity.setRemainCount(_count);
                    tokenBalanceEntity.setCreator(_from);
                    tokenBalanceEntity.setCreateTime(String.valueOf(System.currentTimeMillis() / 1000));
                    tokenBalanceService.insert(tokenBalanceEntity);
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
}
