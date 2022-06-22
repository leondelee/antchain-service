package com.xunta.antchainservice.controller;

//import com.antfinancial.mychain.baas.tool.restclient.RestClient;
//import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.xunta.antchainservice.bean.contract.BatchMintResponse;
import com.xunta.antchainservice.bean.response.Response;
import com.xunta.antchainservice.bean.response.ResponseBuilder;
import com.xunta.antchainservice.config.LogicConfig;
import com.xunta.antchainservice.contracts.MyERC1155;
import com.xunta.antchainservice.entity.my_erc1155.BelongMapperEntity;
import com.xunta.antchainservice.service.UserService;
import com.xunta.antchainservice.service.my_erc1155.BelongMapperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContractController {
    // 藏品接口

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    BelongMapperService belongMapperService;

    @Autowired
    private MyERC1155 myERC1155;

    @GetMapping("/api/contract/batch_mint")
    public Response batchMint() throws Exception {
//        myERC1155.batchMint("123", 10, "123");
        BelongMapperEntity belongMapperEntity = new BelongMapperEntity();
        belongMapperEntity.setTokenId(111111L);
        belongMapperEntity.setCreator("hhh");
        belongMapperEntity.setOwner("xxx");
        belongMapperEntity.setRemainCount(10000L);
        belongMapperService.update(belongMapperEntity);
        BatchMintResponse rsp = new BatchMintResponse();
        return ResponseBuilder.ok(rsp);
    }
}
