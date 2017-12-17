package com.journal.util;

public class AuthUtil {

    public  static String generateToken(){
        String uid = java.util.UUID.randomUUID().toString();
        return uid.replace("-", "");
    }
}
