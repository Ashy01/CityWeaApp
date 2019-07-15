package com.example.weathertestapp.service.API;


import com.example.weathertestapp.service.models.CitiesWeatherModel;
import com.example.weathertestapp.service.models.WeatherModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("/data/2.5/weather")
    Call<WeatherModel> getWeatherDataByCityCode(@Query("q") String q, @Query("appid") String appId);

    @GET("/data/2.5/group")
    Call<CitiesWeatherModel> getWeatherForAllCountries(@Query("id") String id, @Query("appid") String appid);
}