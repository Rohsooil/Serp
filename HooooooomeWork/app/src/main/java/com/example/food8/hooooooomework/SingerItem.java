package com.example.food8.hooooooomework;

import android.os.Parcel;
import android.os.Parcelable;

public class SingerItem implements Parcelable {

    int resId;//이미지 리소스 id
    String name;//이름
    String company;//소속
    String song;//노래

    //생성자
    public SingerItem(int resId, String name, String company, String song ) {
        this.resId = resId;
        this.name = name;
        this.company = company;
        this.song = song;
    }


    protected SingerItem(Parcel in) {
        resId = in.readInt();
        name = in.readString();
        company = in.readString();
        song = in.readString();
    }

    public static final Creator<SingerItem> CREATOR = new Creator<SingerItem>() {
        @Override
        public SingerItem createFromParcel(Parcel in) {
            return new SingerItem(in);
        }

        @Override
        public SingerItem[] newArray(int size) {
            return new SingerItem[size];
        }
    };

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(resId);
        dest.writeString(name);
        dest.writeString(company);
        dest.writeString(song);
    }
}