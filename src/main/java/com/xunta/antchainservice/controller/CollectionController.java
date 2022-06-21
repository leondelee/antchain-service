package com.xunta.antchainservice.controller;

//import com.antfinancial.mychain.baas.tool.restclient.RestClient;
//import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.antfinancial.mychain.baas.tool.restclient.RestClient;
import com.xunta.antchainservice.bean.CollectionDetailRequest;
import com.xunta.antchainservice.bean.CollectionDetailResponse;
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
public class CollectionController {
    // 藏品接口

    private static final Logger logger = LoggerFactory.getLogger(CollectionController.class);

    @Autowired
    private LogicConfig logicConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private RestClient restClient;

    @Autowired
    ContractFlow contractFlow;


    @GetMapping("/api/collection/{collection_id}")
    public Response getDetail(@PathVariable("collection_id") String collectionId) throws Exception {
        contractFlow.runFlow();

        //请求参数
        logger.info("collectionId={}", collectionId);

        //数据库操作
        User user = userService.getUserByUserId(2L);
        logger.info("user is {}", user);

        CollectionDetailResponse rsp = new CollectionDetailResponse();
        rsp.collectionId = collectionId;
        if (user != null) {
            rsp.name = user.getUsername();
        }
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/collection/{collection_id}/update")
    public Response updateDetail(@PathVariable("collection_id") String collectionId,
                                 @RequestBody CollectionDetailRequest detailBean) {
        //请求参数
        logger.info("collectionId={} detailBean={}", collectionId, detailBean);

        //yaml配置变量
        logger.info("logic.variable={}", logicConfig.getVariable());

        CollectionDetailResponse rsp = new CollectionDetailResponse();
        rsp.collectionId = detailBean.collectionId;
        rsp.name = detailBean.name;
        return ResponseBuilder.ok(rsp);
    }
}
