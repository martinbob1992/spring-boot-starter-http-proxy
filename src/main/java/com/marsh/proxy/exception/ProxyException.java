package com.marsh.proxy.exception;

/**
 * @author Marsh
 * @date 2021-12-16日 10:34
 */
public class ProxyException extends RuntimeException{

    protected String message;

    public ProxyException(String message){
        super(message);
        this.message = message;
    }
}
