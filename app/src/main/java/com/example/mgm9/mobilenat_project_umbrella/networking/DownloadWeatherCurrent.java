package com.example.mgm9.mobilenat_project_umbrella.networking;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;

import com.example.mgm9.mobilenat_project_umbrella.MainActivity;
import com.example.mgm9.mobilenat_project_umbrella.R;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by MGM9 on 9/17/2017.
 */

public class DownloadWeatherCurrent extends AsyncTask <String, Void, String> {

    private String mLocation, mZipcode, mTemp, mWeatherType;
    private Exception mException;
    private MainActivity mMainActivity;
    private String theTemp, getTempChoice;

    // CONSTRUCTOR
    public DownloadWeatherCurrent(MainActivity mainActivity, String temp){
        mMainActivity = mainActivity;
        theTemp = temp;
    }

    // Get current weather details for given zipcode.
    @Override
    protected String doInBackground(String... params) {

        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream)
            );

            StringBuilder stringBuilder = new StringBuilder();
            String inputString;
            while((inputString = bufferedReader.readLine()) != null){
                stringBuilder.append(inputString);
            }

            JSONObject topLevel = new JSONObject(stringBuilder.toString());
            JSONObject secLevel = topLevel.getJSONObject("current_observation");
            JSONObject thrLevel = secLevel.getJSONObject("display_location");

            mLocation = String.valueOf(thrLevel.getString("full"));
            mZipcode = String.valueOf(thrLevel.getString("zip"));

            if(theTemp.equalsIgnoreCase("f")){
                mTemp = String.valueOf(secLevel.getDouble("temp_f"));
                getTempChoice = "f";
            }
            else{
                mTemp = String.valueOf(secLevel.getDouble("temp_c"));
                getTempChoice = "c";
            }

            mWeatherType = String.valueOf(secLevel.getString("weather"));

            connection.disconnect();

        } catch (Exception e) {
            mException = e;
        }

        return null;
    }

    // Set up main layout with provided details.
    //   This takes up top half of screen.
    @Override
    protected void onPostExecute(String weather) {

        ActionBar actionBar = this.mMainActivity.getSupportActionBar();

        actionBar.setTitle(mLocation);

        Double temp = Double.parseDouble(mTemp);

        if(getTempChoice.equalsIgnoreCase("f")) {

            if (temp > 60.0) {
                this.mMainActivity.mLinearLayout.setBackgroundColor(ContextCompat.getColor(this.mMainActivity, R.color.colorWarm));
                ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mMainActivity, R.color.colorWarm));
                actionBar.setBackgroundDrawable(colorDrawable);
            } else {
                this.mMainActivity.mLinearLayout.setBackgroundColor(ContextCompat.getColor(this.mMainActivity, R.color.colorCool));
                ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mMainActivity, R.color.colorCool));
                actionBar.setBackgroundDrawable(colorDrawable);
            }
        }
        else {
            if (temp > 15.5) {
                this.mMainActivity.mLinearLayout.setBackgroundColor(ContextCompat.getColor(this.mMainActivity, R.color.colorWarm));
                ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mMainActivity, R.color.colorWarm));
                actionBar.setBackgroundDrawable(colorDrawable);
            } else {
                this.mMainActivity.mLinearLayout.setBackgroundColor(ContextCompat.getColor(this.mMainActivity, R.color.colorCool));
                ColorDrawable colorDrawable = new ColorDrawable(ContextCompat.getColor(this.mMainActivity, R.color.colorCool));
                actionBar.setBackgroundDrawable(colorDrawable);
            }
        }


        this.mMainActivity.mTemp.setText(mTemp + "\u00b0");
        this.mMainActivity.mWeatherType.setText(mWeatherType);
    }
}
