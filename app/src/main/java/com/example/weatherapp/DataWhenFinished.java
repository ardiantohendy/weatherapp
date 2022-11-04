package com.example.weatherapp;

import com.example.weatherapp.model.Weather;

public interface DataWhenFinished {
    void processFinished(Weather weatherEnd);
}
