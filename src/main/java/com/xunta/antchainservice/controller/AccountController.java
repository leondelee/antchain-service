package com.xunta.antchainservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.xunta.antchainservice.bean.account.AccountNameResponse;
import com.xunta.antchainservice.bean.response.Response;
import com.xunta.antchainservice.bean.response.ResponseBuilder;
import com.xunta.antchainservice.config.LogicConfig;
import com.xunta.antchainservice.flow.AccountFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private LogicConfig logicConfig;

    @Autowired
    AccountFlow accountFlow;

    @GetMapping("/api/account/get_address_from_name")
    public Response getAddressFromName(@RequestParam("account_name") String accountName) throws Exception {
        //请求参数
        logger.info("account name={}", accountName);
        AccountNameResponse rsp = new AccountNameResponse();
        rsp.accountAddress = accountFlow.getAccountAddressFromName(accountName);
        rsp.accountName = accountName;
        return ResponseBuilder.ok(rsp);
    }
}
