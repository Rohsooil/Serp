package com.example.user.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by food8 on 2017-08-22.
 */

public class UserInfo implements Parcelable {
    private String userName ="";
    private String userId = "";
    private String userEmail ="";
    private String userPw = "";
    private String userPhone = "";
    private String userBirth = "";
    private String userGender = "";

    public UserInfo(){
        this.userBirth = "";
        this.userId = "";
        this.userEmail ="";
        this.userPw = "";
        this.userPhone = "";
        this.userBirth = "";
        this.userGender = "";
    }

    public UserInfo(String userName, String userId, String userEmail, String userPw,
                    String userPhone, String userBirth, String userGender){
        this.userName = userName;
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userPhone = userPhone;
        this.userBirth = userBirth;
        this.userGender = userGender;
    }

    protected UserInfo(Parcel in) {
        userName = in.readString();
        userId = in.readString();
        userEmail = in.readString();
        userPw = in.readString();
        userPhone = in.readString();
        userBirth = in.readString();
        userGender = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    public String getUserName(){
        return this.userName;
    }
    public String getUserId(){
        return this.userId;
    }
    public String getUserEmail(){
        return this.userEmail;
    }
    public String getUserPw(){
        return this.userPw;
    }
    public String getUserPhone(){
        return this.userPhone;
    }
    public String getUserBirth(){
        return this.userBirth;
    }
    public String getUserGender(){ return  this.userGender; }

    public void setUserName(String userName){ this.userName = userName; }
    public void setUserId(String userId){this.userId = userId;}
    public void setUserEmail(String userEmail){this.userEmail = userEmail;}
    public void setUserPw(String userPw){this.userPw = userPw;}
    public void setUserPhone(String userPhone){this.userPhone = userPhone;}
    public void setUserBirth(String userBirth){this.userBirth = userBirth;}
    public void setUserGender(String userGender){this.userGender = userGender;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userName);
        dest.writeString(userId);
        dest.writeString(userEmail);
        dest.writeString(userPw);
        dest.writeString(userPhone);
        dest.writeString(userBirth);
        dest.writeString(userGender);
    }
}
