package com.sdbean.splashad;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Michael
 * Data: 2020/2/19 11:53
 * Desc: 开屏广告实体类
 */
public class SplashAdBean implements Parcelable {

    private String md5;
    private String type;
    private String resName;
    private String resUrl;
    private String adUrl;
    private int stayTime;
    private String displayTime;

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getType() {
        return type == null ? "jpg" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResUrl() {
        return resUrl;
    }

    public void setResUrl(String resUrl) {
        this.resUrl = resUrl;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public int getStayTime() {
        return stayTime <= 0 ? 5 : stayTime;
    }

    public void setStayTime(int stayTime) {
        this.stayTime = stayTime;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.md5);
        dest.writeString(this.type);
        dest.writeString(this.resName);
        dest.writeString(this.resUrl);
        dest.writeString(this.adUrl);
        dest.writeInt(this.stayTime);
        dest.writeString(this.displayTime);
    }

    public SplashAdBean() {
    }

    protected SplashAdBean(Parcel in) {
        this.md5 = in.readString();
        this.type = in.readString();
        this.resName = in.readString();
        this.resUrl = in.readString();
        this.adUrl = in.readString();
        this.stayTime = in.readInt();
        this.displayTime = in.readString();
    }

    public static final Creator<SplashAdBean> CREATOR = new Creator<SplashAdBean>() {
        @Override
        public SplashAdBean createFromParcel(Parcel source) {
            return new SplashAdBean(source);
        }

        @Override
        public SplashAdBean[] newArray(int size) {
            return new SplashAdBean[size];
        }
    };
}
