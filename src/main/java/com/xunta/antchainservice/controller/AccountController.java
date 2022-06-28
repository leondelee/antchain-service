package com.xunta.antchainservice.controller;

import com.xunta.antchainservice.bean.account.CreateAccountRequest;
import com.xunta.antchainservice.bean.account.CreateAccountResponse;
import com.xunta.antchainservice.bean.response.Response;
import com.xunta.antchainservice.bean.response.ResponseBuilder;
import com.xunta.antchainservice.service.ChainAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ChainAccountService chainAccountService;


    @PostMapping("/api/account/create_chain_account")
    public Response getAddressFromName(@RequestBody CreateAccountRequest createAccountRequest) throws Exception {
        //请求参数
        CreateAccountResponse rsp = chainAccountService.createAccount(createAccountRequest.accountName);

        return ResponseBuilder.ok(rsp);
    }
}
