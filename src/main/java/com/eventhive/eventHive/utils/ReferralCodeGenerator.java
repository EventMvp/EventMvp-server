package com.eventhive.eventHive.utils;

import java.util.UUID;

public class ReferralCodeGenerator {
    public static String generateReferralCode(){
        return UUID.randomUUID().toString().replace("-","").substring(0,8).toUpperCase();
    }
}
