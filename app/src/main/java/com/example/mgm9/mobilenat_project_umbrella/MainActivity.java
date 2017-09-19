package com.example.mgm9.mobilenat_project_umbrella;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mgm9.mobilenat_project_umbrella.networking.DownloadWeatherCurrent;
import com.example.mgm9.mobilenat_project_umbrella.networking.DownloadWeatherForecast;

import static com.example.mgm9.mobilenat_project_umbrella.R.id.action_settings;

public class MainActivity extends AppCompatActivity {

    public TextView mTemp, mWeatherType;
    public LinearLayout mLinearLayout, mForecastBGLayout, mForecastFGLayout1;

    public TextView mTimeTV1, mTimeTV2, mTimeTV3, mTimeTV4;
    public ImageView mImageTV1, mImageTV2, mImageTV3, mImageTV4;
    public TextView mTempTV1, mTempTV2, mTempTV3, mTempTV4;

    public TextView mTimeTV5, mTimeTV6, mTimeTV7, mTimeTV8;
    public ImageView mImageTV5, mImageTV6, mImageTV7, mImageTV8;
    public TextView mTempTV5, mTempTV6, mTempTV7, mTempTV8;

    public TextView mTodayTV;

    public View mBorderV;

    private String zip = "", theTemp = "f";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkEmptyZip(zip);

    }

    // Check if no zip is available.
    //   If zip is empty, prompt user to enter a zipcode.
    public void checkEmptyZip(String theZip){

        if (theZip.isEmpty()) {

            final EditText taskEditText = new EditText(MainActivity.this);

            mLinearLayout = (LinearLayout) findViewById(R.id.full_layout);
            mLinearLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorGray));

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("ZIPCODE")
                    .setMessage("Please Enter A Zipcode To View Weather")
                    .setView(taskEditText)
                    .setCancelable(false)
                    .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            mLinearLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
                            zip = String.valueOf(taskEditText.getText());
                            displayForecast(zip, theTemp);
                        }
                    })
                    .create();
            dialog.show();
        }
    }

    // Display Forecast using provided zipcode.
    public void displayForecast(String zipcode, String temp){

        String url1 = String.format("http://api.wunderground.com/api/f3f27f0f1d0eb524/conditions/q/%s.json",
                zipcode);

        String url2 = String.format("http://api.wunderground.com/api/f3f27f0f1d0eb524/hourly10day/q/%s.json",
                zipcode);

        theTemp = temp;

        mTemp = (TextView) findViewById(R.id.txt_temp);
        mWeatherType = (TextView) findViewById(R.id.txt_weather);

        mLinearLayout = (LinearLayout) findViewById(R.id.layout_main);

        mTimeTV1 = (TextView) findViewById(R.id.txt_time1);
        mTimeTV2 = (TextView) findViewById(R.id.txt_time2);
        mTimeTV3 = (TextView) findViewById(R.id.txt_time3);
        mTimeTV4 = (TextView) findViewById(R.id.txt_time4);
        mTimeTV5 = (TextView) findViewById(R.id.txt_time5);
        mTimeTV6 = (TextView) findViewById(R.id.txt_time6);
        mTimeTV7 = (TextView) findViewById(R.id.txt_time7);
        mTimeTV8 = (TextView) findViewById(R.id.txt_time8);

        mImageTV1 = (ImageView) findViewById(R.id.txt_image1);
        mImageTV2 = (ImageView) findViewById(R.id.txt_image2);
        mImageTV3 = (ImageView) findViewById(R.id.txt_image3);
        mImageTV4 = (ImageView) findViewById(R.id.txt_image4);
        mImageTV5 = (ImageView) findViewById(R.id.txt_image5);
        mImageTV6 = (ImageView) findViewById(R.id.txt_image6);
        mImageTV7 = (ImageView) findViewById(R.id.txt_image7);
        mImageTV8 = (ImageView) findViewById(R.id.txt_image8);

        mTempTV1 = (TextView) findViewById(R.id.txt_temp1);
        mTempTV2 = (TextView) findViewById(R.id.txt_temp2);
        mTempTV3 = (TextView) findViewById(R.id.txt_temp3);
        mTempTV4 = (TextView) findViewById(R.id.txt_temp4);
        mTempTV5 = (TextView) findViewById(R.id.txt_temp5);
        mTempTV6 = (TextView) findViewById(R.id.txt_temp6);
        mTempTV7 = (TextView) findViewById(R.id.txt_temp7);
        mTempTV8 = (TextView) findViewById(R.id.txt_temp8);

        mForecastBGLayout = (LinearLayout) findViewById(R.id.layout_forecast_bg);

        mForecastFGLayout1 = (LinearLayout) findViewById(R.id.layout_forecast_fg1);

        mTodayTV = (TextView) findViewById(R.id.txt_today);

        mBorderV = (View) findViewById(R.id.view_border);

        new DownloadWeatherCurrent(this,theTemp).execute(url1);
        new DownloadWeatherForecast(this,theTemp).execute(url2);

    }

    // Create Options Menu.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // When user clicks on Options Item, user will be taken to settings page
    //   where zipcode can be altered and temp type can be selected.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case action_settings:
                Intent intent = new Intent(MainActivity.this.getApplicationContext(),SettingsActivity.class);
                intent.putExtra("Zipcode",zip);
                startActivityForResult(intent,1);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Retrieve new zipcode and temperature type from Settings Activity.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(data == null){
                return;
            }
            else {
                String newZipcode = data.getStringExtra("Zipcode");
                String newTemp = data.getStringExtra("Temp");

                zip = newZipcode;

                displayForecast(newZipcode,newTemp);
            }

        }
        else {
            return;
        }
    }




}
