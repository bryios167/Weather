package com.example.mgm9.mobilenat_project_umbrella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingsActivity extends AppCompatActivity {

    private EditText zipET;
    private Button btnSubmit;
    private RadioGroup radioTempGroup;
    private RadioButton radioTempButton;
    private String zipCode, theTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Retrieve zipcode from parent activity and set as text.
        Bundle extras = getIntent().getExtras();

        zipET = (EditText)findViewById(R.id.txt_edit_zipcode);

        if (extras != null){
            zipCode = extras.getString("Zipcode");

            zipET.setText(zipCode);
        }

        SetUpListeners();
    }

    public void SetUpListeners(){

        radioTempGroup = (RadioGroup) findViewById(R.id.radioTemp);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioTempGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioTempButton = (RadioButton) findViewById(selectedId);

                /*
                Toast.makeText(MyAndroidAppActivity.this,
                        radioSexButton.getText(), Toast.LENGTH_SHORT).show();
                 */

                String zipcode = zipET.getText().toString();

                String radioTemp;

                if((radioTempButton.getText().toString()).equalsIgnoreCase("Fahrenheit")){
                    radioTemp = "f";
                }
                else {
                    radioTemp = "c";
                }

                Intent saveWeather = new Intent();

                saveWeather.putExtra("Zipcode", zipcode);
                saveWeather.putExtra("Temp", radioTemp);

                setResult(1, saveWeather);
                finish();
            }
        });
    }

    /*
    @Override
    public void onBackPressed() {
        Intent saveWeather = new Intent();

        saveWeather.putExtra("Zipcode", zipCode);
        saveWeather.putExtra("Temp", theTemp);

        setResult(1, saveWeather);
        finish();
    }
    */
}
