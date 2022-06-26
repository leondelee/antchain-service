package com.xunta.antchainservice.controller;

//import com.antfinancial.mychain.baas.tool.restclient.RestClient;
//import com.antfinancial.mychain.baas.tool.restclient.RestClientProperties;
import com.xunta.antchainservice.bean.contract.*;
import com.xunta.antchainservice.bean.response.Response;
import com.xunta.antchainservice.bean.response.ResponseBuilder;
import com.xunta.antchainservice.contracts.MyERC1155;
import com.xunta.antchainservice.service.my_erc1155.TokenBalanceService;
import com.xunta.antchainservice.service.my_erc1155.TokenTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/api/contract/erc1155/get_all_batches")
    public Response getAllBatches() throws Exception {
        BatchListResponse rsp = myERC1155.getAllBatches();
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_batch_info")
    public Response getBatchInfo(@RequestParam("token_id") Long tokenId) throws Exception {
        BatchInfoResponse rsp = myERC1155.getBatchInfo(tokenId);
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_token_info")
    public Response getTokenInfo(@RequestParam("token_id") Long tokenId, @RequestParam("sub_token_id") Long subTokenId) throws Exception {
        TokenInfoResponse rsp = myERC1155.getTokenInfo(tokenId, subTokenId);
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_batch_count")
    public Response getBatchCount() throws Exception {
        long count = myERC1155.getBatchCount();
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = String.valueOf(count);
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_token_mint_count")
    public Response getTokenMintCount(@RequestParam("token_id") Long tokenId) throws Exception {
        long count = myERC1155.getTokenMintCount(tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = String.valueOf(count);
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_token_remain_count")
    public Response getTokenRemainCount(@RequestParam("token_id") Long tokenId) throws Exception {
        long count = myERC1155.getTokenRemainCount(tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = String.valueOf(count);
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_token_uri")
    public Response getTokenUri(@RequestParam("token_id") Long tokenId) {
        String uri = myERC1155.getTokenUri(tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = uri;
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_token_creator")
    public Response getTokenCreator(@RequestParam("token_id") Long tokenId) {
        String uri = myERC1155.getTokenCreator(tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = uri;
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_sub_token_owner")
    public Response getSubTokenOwner(@RequestParam("token_id") Long tokenId, @RequestParam("sub_token_id") Long subTokenId) {
        String owner = myERC1155.getSubTokenOwner(tokenId, subTokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = owner;
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_token_create_time")
    public Response getTokenCreateTime(@RequestParam("token_id") Long tokenId) {
        String data = myERC1155.getTokenCreateTime(tokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = data;
        return ResponseBuilder.ok(rsp);
    }

    @GetMapping("/api/contract/erc1155/get_sub_token_last_update_time")
    public Response getSubTokenLastUpdateTime(@RequestParam("token_id") Long tokenId, @RequestParam("sub_token_id") Long subTokenId) {
        String data = myERC1155.getSubTokenLastUpdateTime(tokenId, subTokenId);
        ContractCallResponse rsp = new ContractCallResponse();
        rsp.data = data;
        return ResponseBuilder.ok(rsp);
    }

    // transaction
    @GetMapping("/api/contract/erc1155/batch_mint")
    public Response batchMint(@RequestParam("uri") String uri, @RequestParam("count") Long count, @RequestParam("from") String from) throws Exception {
        ContractTxResponse rsp = myERC1155.batchMint(uri, count, from);
        if(rsp.success) return ResponseBuilder.ok(rsp);
        else return ResponseBuilder.error(rsp.message);
    }

    @GetMapping("/api/contract/erc1155/transfer")
    public Response transfer(@RequestParam("token_id") Long tokenId, @RequestParam("sub_token_id") Long subTokenId, @RequestParam("from") String from, @RequestParam("to") String to) throws Exception {
        ContractTxResponse rsp = myERC1155.transfer(tokenId, subTokenId, from, to);
        if(rsp.success) return ResponseBuilder.ok(rsp);
        else return ResponseBuilder.error(rsp.message);
    }

    @GetMapping("/api/contract/erc1155/burn")
    public Response transfer(@RequestParam("token_id") Long tokenId, @RequestParam("sub_token_id") Long subTokenId, @RequestParam("from") String from) throws Exception {
        ContractTxResponse rsp = myERC1155.burn(tokenId, subTokenId, from);
        if(rsp.success) return ResponseBuilder.ok(rsp);
        else return ResponseBuilder.error(rsp.message);
    }
}
