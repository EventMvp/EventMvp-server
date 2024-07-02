package com.eventhive.eventHive.Exceptions;

public class VoucherNotExistException extends RuntimeException{
    public VoucherNotExistException(String msg){
        super(msg);
    }
}
