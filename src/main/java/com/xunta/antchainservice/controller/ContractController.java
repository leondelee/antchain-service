package com.xunta.antchainservice.controller;

//import com.antfinancial.mychain.baas.tool.restclient.RestClient;
//import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.xunta.antchainservice.bean.CollectionDetailRequest;
import com.xunta.antchainservice.bean.CollectionDetailResponse;
import com.xunta.antchainservice.bean.ContractResponse;
import com.xunta.antchainservice.bean.response.Response;
import com.xunta.antchainservice.bean.response.ResponseBuilder;
import com.xunta.antchainservice.config.LogicConfig;
import com.xunta.antchainservice.entity.User;
import com.xunta.antchainservice.flow.ContractFlow;
import com.xunta.antchainservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContractController {
    // 藏品接口

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private LogicConfig logicConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private RestClient restClient;

    @Autowired
    ContractFlow contractFlow;


    @GetMapping("/api/contract/contract_call")
    public Response getDetail() throws Exception {
        contractFlow.runFlow();


        //数据库操作
        ContractResponse rsp = new ContractResponse();
        return ResponseBuilder.ok(rsp);
    }
}
