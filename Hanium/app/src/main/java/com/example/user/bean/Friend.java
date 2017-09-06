package com.example.user.bean;

/**
 * Created by food8 on 2017-07-12.
 */

public class Friend {
    private String name = "";
    private String mail ="";
    private String phone ="";
    private String profile = "";
    private String nickname = "";
    private int isBlock = 0;

    public Friend(String name){
        this.name = name;
    }
    public Friend(String name,String mail){
        this.name = name;
        this.mail = mail;
    }
    public Friend(String name,String mail,int isBlock){
        this.name = name;
        this.mail = mail;
        this.isBlock = isBlock;
    }
    public Friend(String name,String mail,String phone, String profile, String nickname, int isBlock){
        this.name = name;
        this.mail = mail;
        this.isBlock = isBlock;
        this.phone = phone;
        this.profile = profile;
        this.nickname = nickname;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setMail(String mail){ this.mail = mail; }
    public void setIsBlock(int isBlock) { this.isBlock = isBlock; }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setProfile(String profile) { this.profile = profile; }

    public String getName(){
        return this.name;
    }
    public String getMail(){ return this.mail; }
    public int getIsBlock(){ return this.isBlock; }
    public String getPhone() {return this.phone;}
    public String getProfile(){ return this.profile; }
    public String getNickname(){ return this.nickname; }
}
