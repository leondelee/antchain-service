package com.xunta.antchainservice.controller;

import com.xunta.antchainservice.bean.contract.ContractTxResponse;
import com.xunta.antchainservice.bean.response.Response;
import com.xunta.antchainservice.bean.response.ResponseBuilder;
import com.xunta.antchainservice.contracts.MyERC1155;
import com.xunta.antchainservice.entity.my_erc1155.TokenBalanceEntity;
import com.xunta.antchainservice.service.my_erc1155.TokenBalanceService;
import com.xunta.antchainservice.service.my_erc1155.TokenTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    TokenTrackerService tokenTrackerService;

    @Autowired
    TokenBalanceService tokenBalanceService;

    @Autowired
    private MyERC1155 myERC1155;

    @GetMapping("/api/test")
    public Response test() throws Exception {
//        // search all by token id
//        List<TokenTrackerEntity> res = tokenTrackerService.getNByTokenId(123L);
//        logger.info("res is: {}", res);

//        // search one by sub token id
//        TokenTrackerEntity res = tokenTrackerService.selectOneBySubTokenId(123L, 2L);
//        logger.info("res is: {}", res);

//        // insert
//        TokenTrackerEntity tokenTrackerEntity = new TokenTrackerEntity();
//        tokenTrackerEntity.setTokenId(222L);
//        tokenTrackerEntity.setSubTokenId(1L);
//        tokenTrackerEntity.setCreator("llw");
//        tokenTrackerEntity.setOwner("yx");
//        tokenTrackerEntity.setCreateTime("12345");
//        tokenTrackerEntity.setLastUpdateTime("1234");
//        tokenTrackerService.insert(tokenTrackerEntity);

//        // update
//        TokenTrackerEntity tokenTrackerEntity = new TokenTrackerEntity();
//        tokenTrackerEntity.setTokenId(222L);
//        tokenTrackerEntity.setSubTokenId(0L);
//        tokenTrackerEntity.setOwner("yxxxx");
//        tokenTrackerService.update(tokenTrackerEntity);

//        // delete all
//        tokenTrackerService.deleteAllByTokenId(222L);

//        // delete one by sub token id
//        tokenTrackerService.deleteOneBySubTokenId(123L, 0L);

        TokenBalanceEntity tokenBalanceEntity = new TokenBalanceEntity();
        tokenBalanceEntity.setTokenId(111L);
        tokenBalanceEntity.setRemainCount(100L);
        tokenBalanceEntity.setCreateTime("1234");
        tokenBalanceService.insert(tokenBalanceEntity);

        ContractTxResponse rsp = new ContractTxResponse();
        return ResponseBuilder.ok(rsp);
    }
}
