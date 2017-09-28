package com.test.utils;

public class BusinessException extends Exception {

    public BusinessException(){
        super("Some unknown error occur. Please contact dev team.");
    }

    public BusinessException(String message){
        super(message);
    }


}
