package com.example.weathertestapp.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.weathertestapp.base.WeatherApplication;
import com.example.weathertestapp.service.API.WeatherService;
import com.example.weathertestapp.service.models.CitiesWeatherModel;
import com.example.weathertestapp.service.models.WeatherModel;
import com.example.weathertestapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProjectRepository {

    private static WeatherService weatherService;

    public static WeatherService getInstance() {
        weatherService = WeatherApplication.initRestClient();
        return weatherService;
    }
    public LiveData<CitiesWeatherModel> getWeathersForCountries() {
        final MutableLiveData<CitiesWeatherModel> data = new MutableLiveData<>();

        weatherService.getWeatherForAllCountries(Constants.DEFAULT_COUNTRYCODES, Constants.API_KEY).enqueue(new Callback<CitiesWeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<CitiesWeatherModel> call, @NonNull Response<CitiesWeatherModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CitiesWeatherModel> call, @NonNull Throwable t) {

            }
        });

        return data;
    }
    public LiveData<WeatherModel> getWeatherDataByCityCode(String q) {
        final MutableLiveData<WeatherModel> data = new MutableLiveData<>();

        weatherService.getWeatherDataByCityCode(q, Constants.API_KEY).enqueue(new Callback<WeatherModel>() {

            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {

            }
        });

        return data;
    }
}
