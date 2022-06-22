/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.xunta.antchainservice.flow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mychain.sdk.api.utils.Utils;
import com.alipay.mychain.sdk.common.VMTypeEnum;
import com.alipay.mychain.sdk.domain.account.Identity;
import com.alipay.mychain.sdk.utils.ByteUtils;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.model.CallRestBizParam;
import com.antfinancial.mychain.baas.tool.restclient.model.ClientParam;
import com.antfinancial.mychain.baas.tool.restclient.model.Method;
import com.antfinancial.mychain.baas.tool.restclient.response.BaseResp;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.xunta.antchainservice.controller.CollectionController;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.encoders.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigInteger;
import java.util.UUID;

import com.alibaba.fastjson.JSONArray;

/**
 * 合约相关场景下操作流，示例代码提供
 */
@Service
public class ContractFlow {
    private static final Logger logger = LoggerFactory.getLogger(ContractFlow.class);
//    private static final String CONTRACT_NAME = "my_erc1155_v1";
    private static final String CONTRACT_NAME = "my_erc1155_v2";
    @Autowired
    private RestClient restClient;

    @Autowired
    private RestClientProperties restClientProperties;

    public void runFlow() throws Exception {
        System.out.println(callSync());
        //建议在Cloud Ide部署完合约后，在使用下面的调用合约
//        String csHash = callSync();
        //解析合约返回值
//        showOutPut(csHash);
    }

    //调用Solidity合约
    public BaseResp callSync() throws Exception {
        String orderId = "order_" + System.currentTimeMillis();
        JSONArray inputList = new JSONArray();
        inputList.add(Utils.getIdentityByName(restClientProperties.getDefaultAccount()));
//        inputList.add(BigInteger.valueOf(100));
        System.out.println(inputList);
        JSONArray outputTypes = new JSONArray();
        outputTypes.add("uint256");
        CallRestBizParam callRestBizParam=CallRestBizParam.builder()
                .orderId(orderId)
                .bizid(restClientProperties.getBizid())
                .account(restClientProperties.getDefaultAccount())
                .contractName(CONTRACT_NAME)
                .methodSignature("hello_view(identity)")
                .inputParamListStr(JSON.toJSONString(inputList))
                .outTypes(JSON.toJSONString(outputTypes))
                .mykmsKeyId(restClientProperties.getKmsId())
                .method(Method.CALLCONTRACTBIZ)
                .tenantid(restClientProperties.getTenantid())
                .gas(100000L).build();
        return restClient.chainCallForBiz(callRestBizParam);
    }


    //查询交易是否成功
    public void queryResult(String hash) throws Exception {
        Thread.sleep(2000);
        BaseResp queryBaseResp = restClient.chainCall(hash, restClientProperties.getBizid(), "", Method.QUERYRECEIPT);
        System.out.println(queryBaseResp);
    }

    //解析调用合约返回值  （解析返回值请填写show方法中参数，需要使用密钥托管账户）
    public void showOutPut(String hash) throws Exception {
        Thread.sleep(3000);
        BaseResp queryBaseResp = restClient.chainCall(hash, restClientProperties.getBizid(), "", Method.QUERYRECEIPT);
        String s = queryBaseResp.getData();
        String output = s.substring(s.indexOf("output") + 9, s.indexOf("result")).replace("\"", "").replace(",", "");
        if (0 != output.length()) {
            BaseResp show = show(output);
            System.out.println(show);
        }else {
            System.out.println(s);
        }
    }
    private BaseResp show(String output) throws Exception {
        byte[] content = Hex.encode(Base64.decode(output));
        CallRestBizParam callRestBizParam = CallRestBizParam.builder().
                bizid(restClientProperties.getBizid()).method(Method.PARSEOUTPUT).
                tenantid(restClientProperties.getTenantid()).
                orderId("order_" + System.currentTimeMillis()).
                vmTypeEnum(VMTypeEnum.EVM).content(new String(content)).
                abi("[\"bool\"]").                                      //TODO 合约返回值类型需自己根据合约修改
                mykmsKeyId(restClientProperties.getKmsId()).build();    //TODO 默认为application.yaml中KmsId
        BaseResp baseResp = restClient.chainCallForBiz(callRestBizParam);
        assert (baseResp.isSuccess());
        System.out.println("show" + baseResp);
        return baseResp;
    }
}