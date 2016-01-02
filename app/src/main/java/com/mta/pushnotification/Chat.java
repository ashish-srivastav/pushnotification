package com.mta.pushnotification;

/**
 * Created by ashish.srivastava on 12/30/2015.
 */
public class Chat {

    private String messager;

    public String getMessage() {
        return message;
    }

    public String getMessager() {
        return messager;
    }

    private String message;

    public Chat (String mes,String msg){
        message=msg;
        messager=mes;
    }

}
