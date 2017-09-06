package com.example.user.bean;

/**
 * Created by food8 on 2017-07-19.
 */

public class Chat {
    private String chatter = "";
    private String chatTime ="";
    public Chat(String name,String time){
        this.chatter = name;
        this.chatTime = time;
    }
    public void setSender(String name){
        this.chatter = name;
    }
    public String getSender(){
        return this.chatter;
    }
    public void setTime(String time){
        this.chatTime = time;
    }
    public String getTime(){
        return this.chatTime;
    }
}

