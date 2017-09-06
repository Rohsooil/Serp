package com.example.user.bean;

/**
 * Created by TaeYoung on 2017-08-25.
 */

public class Message {
    private String sender = "";
    private String time ="";
    private String content = "";
    public Message(String name,String time,String content){
        this.sender = name;
        this.time = time;
        this.content = content;
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
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }

}