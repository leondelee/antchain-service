package com.xunta.antchainservice.bean.response;

import com.xunta.antchainservice.controller.CollectionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ResponseBuilder.class);

    public static Response ok() {
        return build(Code.SUCCESS, "", null);
    }

    public static Response ok(Object data) {
        return build(Code.SUCCESS, "", data);
    }

    public static Response error(String message) {
        return build(Code.INTERNAL_ERROR, message, null);
    }

    public static Response error(int errorCode, String message) {
        return build(errorCode, message, null);
    }

    public static Response build(int errorCode, String message, Object data) {
        Response response = new Response();
        response.setErrorCode(errorCode);
        response.setMessage(message);
        response.setSuccess(errorCode == Code.SUCCESS);
        response.setData(data);
        return response;
    }
}
