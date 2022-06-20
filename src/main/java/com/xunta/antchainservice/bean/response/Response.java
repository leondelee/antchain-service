package com.xunta.antchainservice.bean.response;

import lombok.Data;

@Data
public class Response {
    private int errorCode;
    private String message;
    private boolean success;
    private Object data;

    public void setErrorCode(int ec) {errorCode = ec;}
    public void setMessage(String msg) {message = msg;}
    public void setSuccess(boolean ss) { success = ss;}
    public void setData(Object d) {data = d;}

}
