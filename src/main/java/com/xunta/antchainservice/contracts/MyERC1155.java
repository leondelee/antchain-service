package com.xunta.antchainservice.contracts;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alipay.mychain.sdk.api.utils.Utils;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.model.CallRestBizParam;
import com.antfinancial.mychain.baas.tool.restclient.model.Method;
import com.antfinancial.mychain.baas.tool.restclient.response.BaseResp;
import com.xunta.antchainservice.flow.ContractFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service
public class MyERC1155 {
    private static final String CONTRACT_NAME = "my_erc1155_v3";
    private static final Logger logger = LoggerFactory.getLogger(MyERC1155.class);

    private Map<String, Long> _belongMap = new HashMap<String, Long>();

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestClientProperties restClientProperties;

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

    public boolean batchMint(String _uri, int _count, String _from) {
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
                    _belongMap.put(_from, tokenIdx);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error batchMint: {}", e.toString());
            return false;
        }

        return true;
    }
}
