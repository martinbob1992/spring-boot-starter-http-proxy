package com.marsh.proxy.exception;

import lombok.Getter;

/**
 * @author Marsh
 * @date 2021-12-16日 10:38
 */
public class DataProxyException extends ProxyException{

    @Getter
    protected int status;

    public DataProxyException(int status, String message){
        super(message);
        this.status = status;
    }
}
