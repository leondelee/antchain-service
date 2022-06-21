package com.xunta.antchainservice.flow;

import com.alibaba.fastjson.JSON;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.model.ClientParam;
import com.antfinancial.mychain.baas.tool.restclient.model.Method;
import com.antfinancial.mychain.baas.tool.restclient.response.BaseResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountFlow {
    @Autowired
    private RestClient restClient;

    @Autowired
    private RestClientProperties restClientProperties;

    public String getAccountAddressFromName(String accountName) throws Exception {
        ClientParam clientParam = restClient.createQueryAccountParam(accountName);
        BaseResp baseResp = restClient.chainCall(clientParam.getHash(), restClientProperties.getBizid(),clientParam.getSignData(), Method.QUERYACCOUNT);
        if(baseResp.getData() != null && JSON.parseObject(baseResp.getData()).containsKey("id")) return JSON.parseObject(baseResp.getData()).get("id").toString();
        return "";
    }
}
