package com.example.food8.projectofgimal;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by food8 on 2017-06-10.
 */

public class Food_info implements Parcelable {
    private String gyojikone;
    private String ilpoom;
    private String spepoom;

    public Food_info(){
        gyojikone = "";
        ilpoom = "";
        spepoom = "";
    }

    protected Food_info(Parcel in) {
        gyojikone = in.readString();
        ilpoom = in.readString();
        spepoom = in.readString();
    }

    public static final Creator<Food_info> CREATOR = new Creator<Food_info>() {
        @Override
        public Food_info createFromParcel(Parcel in) {
            return new Food_info(in);
        }

        @Override
        public Food_info[] newArray(int size) {
            return new Food_info[size];
        }
    };

    public String getGyojikone(){
        return this.gyojikone;
    }
    public String getIlpoom(){
        return this.ilpoom;
    }
    public String getSpepoom(){
        return this.spepoom;
    }

    public void setGyojikone(String gyojikone){
        this.gyojikone = gyojikone;
    }
    public void setIlpoom(String ilpoom){
        this.ilpoom = ilpoom;
    }
    public void setSpepoom(String spepoom){
        this.spepoom = spepoom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gyojikone);
        dest.writeString(ilpoom);
        dest.writeString(spepoom);
    }
}
