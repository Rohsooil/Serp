package com.example.user.bean;

/**
 * Created by food8 on 2017-07-15.
 */

public class Sense {
    private String sender = "";
    private String time ="";
    public Sense(String name, String time){
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