package com.example.food8.projectofgimal;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;


public class Firebase_info implements Serializable{
    private FirebaseAuth mfirebaseAuth;
    private FirebaseUser mfirebaseUser;
    private String mUsername;

    Firebase_info(){
        this.mfirebaseAuth = null;
        this.mfirebaseUser = null;
        this.mUsername = "";
    }

    Firebase_info(FirebaseAuth FAUTH){
        this.mfirebaseAuth = FAUTH;
        this.mfirebaseUser = this.mfirebaseAuth.getCurrentUser();
        this.mUsername = this.mfirebaseUser.getDisplayName().toString();
    }

    Firebase_info(String anony){
        this.mfirebaseAuth = null;
        this.mfirebaseUser = null;
        this.mUsername = anony;
    }

    public void setMfirebaseAuth(FirebaseAuth FAUTH){
        this.mfirebaseAuth = FAUTH;
    }
    public void setMfirebaseUser(FirebaseUser FUSER){
        this.mfirebaseUser = FUSER;
    }
    public void setmUsername(String muser){
        this.mUsername = muser;
    }

    public FirebaseAuth getMfirebaseAuth(){
        return this.mfirebaseAuth;
    }
    public FirebaseUser getMfirebaseUser(){
        return this.mfirebaseUser;
    }
    public String getmUsername(){
        return this.mUsername;
    }

}
