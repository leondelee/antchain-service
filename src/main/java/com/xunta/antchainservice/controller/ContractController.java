package com.xunta.antchainservice.controller;

//import com.antfinancial.mychain.baas.tool.restclient.RestClient;
//import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.xunta.antchainservice.bean.contract.*;
import com.xunta.antchainservice.bean.response.Response;
import com.xunta.antchainservice.bean.response.ResponseBuilder;
import com.xunta.antchainservice.contracts.MyERC1155;
import com.xunta.antchainservice.entity.my_erc1155.TokenBalanceEntity;
import com.xunta.antchainservice.entity.my_erc1155.TokenEntity;
import com.xunta.antchainservice.entity.my_erc1155.TokenTrackerEntity;
import com.xunta.antchainservice.service.my_erc1155.TokenBalanceService;
import com.xunta.antchainservice.service.my_erc1155.TokenTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ContractController {
    // 藏品接口

    private static final Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    TokenTrackerService tokenTrackerService;

    @Autowired
    TokenBalanceService tokenBalanceService;

    @Autowired
    private MyERC1155 myERC1155;

    @PostMapping("/api/contract/erc1155/get_all_batches")
    public Response getAllBatches() throws Exception {
        BatchListResponse rsp = myERC1155.getAllBatches();
        return ResponseBuilder.ok(rsp);
    }


    @PostMapping("/api/contract/erc1155/get_purchased_tokens")
    public Response getPurchasedTokens(@RequestBody ContractCallRequest contractCallRequest ) throws Exception {
        TokenListResponse rsp = new TokenListResponse();
        List<TokenInfoResponse> resultList = new ArrayList<TokenInfoResponse>();
        List<TokenTrackerEntity> tokenTracks = tokenTrackerService.selectNByOwner(contractCallRequest.from);
        for(TokenTrackerEntity tokenTrackerEntity: tokenTracks) {
            TokenInfoResponse tokenInfo = myERC1155.getTokenInfo(tokenTrackerEntity.getTokenId(), tokenTrackerEntity.getSubTokenId());
            resultList.add(tokenInfo);
        }
        rsp.resultList = resultList;
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_batch_info")
    public Response getBatchInfo(@RequestBody ContractCallRequest contractCallRequest) throws Exception {
        BatchInfoResponse rsp = myERC1155.getBatchInfo(contractCallRequest.tokenId);
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_token_info")
    public Response getTokenInfo(@RequestBody ContractCallRequest contractCallRequest) throws Exception {
        TokenInfoResponse rsp = myERC1155.getTokenInfo(contractCallRequest.tokenId, contractCallRequest.subTokenId);
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_token_info_by_hash")
    public Response getTokenInfoByHash(@RequestBody ContractCallRequest contractCallRequest) throws Exception {
        TokenTrackerEntity tokenTrackerEntity = tokenTrackerService.selectOneByHash(contractCallRequest.hash);
        TokenInfoResponse rsp = myERC1155.getTokenInfo(tokenTrackerEntity.getTokenId(), tokenTrackerEntity.getSubTokenId());
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_max_sub_token_id")
    public Response getMaxSubTokenId(@RequestBody ContractCallRequest contractCallRequest) throws Exception {
        Long id = myERC1155.getMaxSubTokenId(contractCallRequest.tokenId);
        Response rsp = new Response();
        rsp.setData(id);
        return ResponseBuilder.ok(id);
    }

    @PostMapping("/api/contract/erc1155/get_batch_count")
    public Response getBatchCount() throws Exception {
        long count = myERC1155.getBatchCount();
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = String.valueOf(count);
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_token_mint_count")
    public Response getTokenMintCount(@RequestBody ContractCallRequest contractCallRequest) throws Exception {
        long count = myERC1155.getTokenMintCount(contractCallRequest.tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = String.valueOf(count);
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_token_remain_count")
    public Response getTokenRemainCount(@RequestBody ContractCallRequest contractCallRequest) throws Exception {
        long count = myERC1155.getTokenRemainCount(contractCallRequest.tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = String.valueOf(count);
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_token_uri")
    public Response getTokenUri(@RequestBody ContractCallRequest contractCallRequest) {
        TokenBalanceEntity tokenBalanceEntity = tokenBalanceService.selectOneByTokenId(contractCallRequest.tokenId);
        String uri =tokenBalanceEntity.getTokenuri();
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = uri;
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_token_creator")
    public Response getTokenCreator(@RequestBody ContractCallRequest contractCallRequest) {
        String uri = myERC1155.getTokenCreator(contractCallRequest.tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = uri;
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_sub_token_owner")
    public Response getSubTokenOwner(@RequestBody ContractCallRequest contractCallRequest) {
        String owner = myERC1155.getSubTokenOwner(contractCallRequest.tokenId, contractCallRequest.subTokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = owner;
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_token_create_time")
    public Response getTokenCreateTime(@RequestBody ContractCallRequest contractCallRequest) {
        String data = myERC1155.getTokenCreateTime(contractCallRequest.tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = data;
        return ResponseBuilder.ok(rsp);
    }

    @PostMapping("/api/contract/erc1155/get_sub_token_last_update_time")
    public Response getSubTokenLastUpdateTime(@RequestBody ContractCallRequest contractCallRequest) {
        String data = myERC1155.getSubTokenLastUpdateTime(contractCallRequest.tokenId, contractCallRequest.subTokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = data;
        return ResponseBuilder.ok(rsp);
    }

    // transaction
    @PostMapping("/api/contract/erc1155/batch_mint")
    public Response batchMint(@RequestBody BatchMintRequest batchMintRequest) throws Exception {
        ContractTxResponse rsp = myERC1155.batchMint(batchMintRequest);
        if(rsp.success) return ResponseBuilder.ok(rsp);
        else return ResponseBuilder.error(rsp.message);
    }

    @PostMapping("/api/contract/erc1155/transfer")
    public Response transfer(@RequestBody TransferRequest transferRequest) throws Exception {
        ContractTxResponse rsp = myERC1155.transfer(transferRequest.tokenId, transferRequest.subTokenId, transferRequest.from, transferRequest.to);
        if(rsp.success) return ResponseBuilder.ok(rsp);
        else return ResponseBuilder.error(rsp.message);
    }

    @PostMapping("/api/contract/erc1155/burn")
    public Response burn(@RequestBody TransferRequest transferRequest) throws Exception {
        ContractTxResponse rsp = myERC1155.burn(transferRequest.tokenId, transferRequest.subTokenId, transferRequest.from);
        if(rsp.success) return ResponseBuilder.ok(rsp);
        else return ResponseBuilder.error(rsp.message);
    }
}
