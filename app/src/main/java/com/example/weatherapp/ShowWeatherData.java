package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.weatherapp.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class ShowWeatherData extends AppCompatActivity {
    private TextView justTest, titleAndLocation, textDesc, txtTemp, txtFeels, txtHumid, txtSpeed, txtClouds, txtPressure;
    private Weather weatherObj2;
    private String tempUrl;
    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "948d123206f74779e1a4300b389c4240";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather_data);
        justTest = findViewById(R.id.receiveDataFromMain);
        titleAndLocation = findViewById(R.id.titleAndLocation);
        textDesc = findViewById(R.id.txtDesc);
        txtTemp = findViewById(R.id.txtTemp);
        txtClouds = findViewById(R.id.txtClouds);
        txtFeels = findViewById(R.id.txtFeels);
        txtHumid = findViewById(R.id.txtHumid);
        txtPressure = findViewById(R.id.txtPressure);
        txtSpeed = findViewById(R.id.txtSpeed);
        tempUrl = "";

        String city = getIntent().getStringExtra("city");
        String country = getIntent().getStringExtra("country");


        if(!country.equals("")) {
            tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
        } else {
            tempUrl = url + "?q=" + city + "&appid=" + appid;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String output = "";
                String titleOutput;
                try {
                    JSONObject jsonRespones = new JSONObject(response);
                    JSONArray jsonArray = jsonRespones.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonRespones.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                    float pressure = jsonObjectMain.getInt("pressure");
                    int humidity = jsonObjectMain.getInt("humidity");
                    JSONObject jsonObjectWind = jsonRespones.getJSONObject("wind");
                    String speed = jsonObjectWind.getString("speed");
                    JSONObject jsonObjectClouds = jsonRespones.getJSONObject("clouds");
                    String clouds = jsonObjectClouds.getString("all");
                    JSONObject jsonObjectSys = jsonRespones.getJSONObject("sys");
                    String countryName = jsonObjectSys.getString("country");
                    String cityName = jsonRespones.getString("name");
                                titleOutput = "Current weather of " + cityName + " (" + countryName + ")";
                                        output ="Temperature: " + df.format(temp) + " °C\n"
                                        + "Feels Like: " + df.format(feelsLike) + " °C\n"
                                        + "Humidity: " + humidity + "%\n"
                                        + "Description: " + description
                                        + "\nWind Speed: " + speed + " m/s"
                                        + "\nCloudiness: " + clouds + "%"
                                        + "\nPressure: " + pressure + " hPa";
//                                weatherObj = new Weather(cityName, countryName,temp,feelsLike,pressure,humidity,speed,clouds, description);
                                titleAndLocation.setText(titleOutput);
                                textDesc.setText(description.toUpperCase());
                                txtTemp.setText("Temperature: " + df.format(temp) + " °C");
                                txtFeels.setText("Feels Like: " + df.format(feelsLike) + " °C");
                                txtHumid.setText("Humidity: " + Integer.toString(humidity) + "%");
                                txtPressure.setText("Pressure: " + Float.toString(pressure) + " hPa");
                                txtClouds.setText("Clouds: " + clouds + "%");
                                txtSpeed.setText("Speed: " + speed + " m/s");

//                                intent.putExtra("weatherData", weatherEnd);

//                                processFinished(weatherObj);
//                                Toast.makeText(MainActivity.this, "test: " + weatherObj.getCityName().toString(), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("tai", "onResponse: " + e);

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);




//        weatherObj2 = (Weather) getIntent().getSerializableExtra("weatherApp");


//        try {
//            Log.d("ok", "onCreate: " + weatherObj2.getCityName());
//        } catch (Exception e) {
//            Toast.makeText(this, "ERROR!!!: " + e.toString(), Toast.LENGTH_SHORT).show();
//        }

    }
}