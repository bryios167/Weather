package com.example.mgm9.mobilenat_project_umbrella.networking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;

import com.example.mgm9.mobilenat_project_umbrella.MainActivity;
import com.example.mgm9.mobilenat_project_umbrella.R;
import com.example.mgm9.mobilenat_project_umbrella.model.Forecast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by MGM9 on 9/18/2017.
 */

public class DownloadWeatherForecast extends AsyncTask<String, Void, String> {

    private Exception mException;
    private MainActivity mMainActivity;
    private Bitmap bm;
    private ArrayList<Forecast> mForecastArrayList;
    private String mTemp;

    // CONSTRUCTOR
    public DownloadWeatherForecast(MainActivity mainActivity, String temp){
        mMainActivity = mainActivity;
        mTemp = temp;
    }

    // Get hourly forecast details for given zipcode.
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
            JSONArray secLevel = topLevel.getJSONArray("hourly_forecast");
            mForecastArrayList = new ArrayList<>(secLevel.length() + 1);


            for (int index = 0; index < 8; index++) {
                JSONObject jsonObject = secLevel.getJSONObject(index);
                JSONObject thrLevel = jsonObject.getJSONObject("FCTTIME");
                JSONObject fffLevel = jsonObject.getJSONObject("temp");

                // Check if the hour is on the same day or not.
                //   If next day, Forecast object will be null.
                if(Integer.parseInt(String.valueOf(thrLevel.getString("mday"))) ==
                        Integer.parseInt(String.valueOf(secLevel.getJSONObject(0).
                                getJSONObject("FCTTIME").getString("mday")))){

                    String time = String.valueOf(thrLevel.getString("civil"));
                    String image = String.valueOf(jsonObject.getString("icon"));

                    String temp;

                    if (mTemp.equalsIgnoreCase("f")) {
                        temp = String.valueOf(fffLevel.getString("english"));
                    }
                    else {
                        temp = String.valueOf(fffLevel.getString("metric"));
                    }

                    InputStream is = new URL("http://icons-ak.wxug.com/i/c/i/" + image + ".gif").openStream();

                    bm = BitmapFactory.decodeStream(is);

                    Forecast forecast = new Forecast(time, bm, temp + "\u00b0");

                    mForecastArrayList.add(forecast);
                }
                else
                {
                    Forecast forecast = new Forecast();
                    mForecastArrayList.add(forecast);
                }
            }

            connection.disconnect();

        } catch (Exception e) {
            mException = e;
        }

        return null;
    }

    // Set up main layout with provided details.
    //   This takes up bottom half of screen.
    @Override
    protected void onPostExecute(String weather) {

        this.mMainActivity.mTimeTV1.setText(mForecastArrayList.get(0).getTime());
        this.mMainActivity.mTimeTV2.setText(mForecastArrayList.get(1).getTime());
        this.mMainActivity.mTimeTV3.setText(mForecastArrayList.get(2).getTime());
        this.mMainActivity.mTimeTV4.setText(mForecastArrayList.get(3).getTime());
        this.mMainActivity.mTimeTV5.setText(mForecastArrayList.get(4).getTime());
        this.mMainActivity.mTimeTV6.setText(mForecastArrayList.get(5).getTime());
        this.mMainActivity.mTimeTV7.setText(mForecastArrayList.get(6).getTime());
        this.mMainActivity.mTimeTV8.setText(mForecastArrayList.get(7).getTime());

        this.mMainActivity.mImageTV1.setImageBitmap(mForecastArrayList.get(0).getImage());
        this.mMainActivity.mImageTV2.setImageBitmap(mForecastArrayList.get(1).getImage());
        this.mMainActivity.mImageTV3.setImageBitmap(mForecastArrayList.get(2).getImage());
        this.mMainActivity.mImageTV4.setImageBitmap(mForecastArrayList.get(3).getImage());
        this.mMainActivity.mImageTV5.setImageBitmap(mForecastArrayList.get(4).getImage());
        this.mMainActivity.mImageTV6.setImageBitmap(mForecastArrayList.get(5).getImage());
        this.mMainActivity.mImageTV7.setImageBitmap(mForecastArrayList.get(6).getImage());
        this.mMainActivity.mImageTV8.setImageBitmap(mForecastArrayList.get(7).getImage());

        this.mMainActivity.mTempTV1.setText(mForecastArrayList.get(0).getTempF());
        this.mMainActivity.mTempTV2.setText(mForecastArrayList.get(1).getTempF());
        this.mMainActivity.mTempTV3.setText(mForecastArrayList.get(2).getTempF());
        this.mMainActivity.mTempTV4.setText(mForecastArrayList.get(3).getTempF());
        this.mMainActivity.mTempTV5.setText(mForecastArrayList.get(4).getTempF());
        this.mMainActivity.mTempTV6.setText(mForecastArrayList.get(5).getTempF());
        this.mMainActivity.mTempTV7.setText(mForecastArrayList.get(6).getTempF());
        this.mMainActivity.mTempTV8.setText(mForecastArrayList.get(7).getTempF());

        this.mMainActivity.mTodayTV.setText("Today");

        this.mMainActivity.mBorderV.setBackgroundColor(ContextCompat.getColor
                (this.mMainActivity, R.color.colorGray));

        this.mMainActivity.mForecastBGLayout.setBackgroundColor(ContextCompat.getColor
                (this.mMainActivity, R.color.colorLightGray));

        this.mMainActivity.mForecastFGLayout1.setBackgroundColor(ContextCompat.getColor
                (this.mMainActivity, R.color.colorWhite));

    }
}


