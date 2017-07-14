package com.example.user.hanium;

/**
 * Created by food8 on 2017-07-15.
 */

public class Message {
    private String sender = "";
    private String time ="";
    public Message(String name,String time){
        this.sender = name;
        this.time = time;
    }
    public void setSender(String name){
        this.sender = name;
    }
    public String getSender(){
        return this.sender;
    }
    public void setTime(String time){
        this.time = time;
    }
    public String getTime(){
        return this.time;
    }
}