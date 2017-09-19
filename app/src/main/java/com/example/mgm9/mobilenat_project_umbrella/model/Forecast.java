package com.example.mgm9.mobilenat_project_umbrella.model;

import android.graphics.Bitmap;

/**
 * Created by MGM9 on 9/18/2017.
 */

// Set up Forecast class to hold forecast details per hour.
public class Forecast {

    private String mTime;
    private Bitmap mImage;
    private String mTempF;

    // CONSTRUCTOR
    public Forecast(String time, Bitmap image, String tempF){
        mTime = time;
        mImage = image;
        mTempF = tempF;
    }

    public Forecast(){

    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public String getTempF() {
        return mTempF;
    }

    public void setTempF(String tempF) {
        mTempF = tempF;
    }
}
