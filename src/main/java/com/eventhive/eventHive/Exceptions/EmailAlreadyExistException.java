package com.eventhive.eventHive.Exceptions;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException(String msg){
        super(msg);
    }
}
