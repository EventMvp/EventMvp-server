package com.eventhive.eventHive.Exceptions;

public class EventTicketNotExistException extends RuntimeException{
    public EventTicketNotExistException (String msg){
        super(msg);
    }
}
