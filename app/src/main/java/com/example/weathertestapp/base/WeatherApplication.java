package com.example.weathertestapp.base;

import android.app.Application;
import android.content.Context;

import com.example.weathertestapp.service.API.WeatherService;
import com.example.weathertestapp.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;



public class WeatherApplication extends Application {

    public static WeatherService initRestClient() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit adapter = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        return adapter.create(WeatherService.class);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

}
