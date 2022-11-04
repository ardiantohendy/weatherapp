package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.weatherapp.model.Weather;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity{
    private Weather weatherObj;
    private EditText editTxtCity, editTxtCountry;
    private Button btnGet;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTxtCity = findViewById(R.id.editTxtCity);
        editTxtCountry = findViewById(R.id.editTxtCountry);
        btnGet = findViewById(R.id.getBtn);
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editTxtCity.getText().toString().trim();
                String country = editTxtCountry.getText().toString().trim();

                if (city.equals("")) {
//                    showDataTxt.setText("City Field can not be EMpty!!!");
                } else {
                    intent = new Intent(MainActivity.this, ShowWeatherData.class);
                    intent.putExtra("city", city);
                    intent.putExtra("country", country);
                    startActivity(intent);
                }
            }
        });

    }
}