package com.xunta.antchainservice.service;

import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.model.CallRestBizParam;
import com.antfinancial.mychain.baas.tool.restclient.model.Method;
import com.antfinancial.mychain.baas.tool.restclient.response.BaseResp;
import com.xunta.antchainservice.bean.account.CreateAccountResponse;
import com.xunta.antchainservice.controller.ContractController;
import com.xunta.antchainservice.dao.CollectionBuyLockerDao;
import com.xunta.antchainservice.entity.CollectionBuyLockerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ChainAccountService {
    private static final Logger logger = LoggerFactory.getLogger(ChainAccountService.class);

    @Autowired
    private RestClient restClient;

    @Autowired
    private RestClientProperties restClientProperties;

    public CreateAccountResponse createAccount(String accountName) {
        CreateAccountResponse rsp = new CreateAccountResponse();
        String orderId = "order_" + System.currentTimeMillis();
        String newKmsId = restClientProperties.getTenantid() + System.currentTimeMillis();
        CallRestBizParam callRestBizParam=CallRestBizParam.builder()
                .orderId(orderId)
                .bizid(restClientProperties.getBizid())
                .account(restClientProperties.getDefaultAccount())
                .mykmsKeyId(restClientProperties.getKmsId())
                .newAccountId(accountName)
                .newAccountKmsId(newKmsId)
                .method(Method.TENANTCREATEACCUNT)
                .tenantid(restClientProperties.getTenantid())
                .gas(200000L).build();
        try {
            BaseResp baseResp = restClient.chainCallForBiz(callRestBizParam);
            if(baseResp == null) {
                rsp.success = false;
                rsp.code = "Empty response";
            }
            else if(! baseResp.isSuccess()) {
                rsp.success = false;
                rsp.code = baseResp.getData();
            }
            else {
                rsp.success = true;
                rsp.code = baseResp.getCode();
                rsp.publicKey = baseResp.getData();
            }
        } catch (Exception e) {
            rsp.success = false;
            rsp.code = e.toString();
            logger.info("res: {}", e.toString());
        }
        return rsp;
    }
}