package com.example.weathertestapp.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.example.weathertestapp.service.models.CitiesWeatherModel;
import com.example.weathertestapp.service.models.WeatherModel;
import com.example.weathertestapp.utils.Constants;

import retrofit2.Call;

import static com.example.weathertestapp.viewmodels.ProjectRepository.getInstance;



public class ProjectViewModel extends ViewModel {

    private Call<CitiesWeatherModel> countriesListObservable;
    private Call<WeatherModel> countryWeatherObservable;

    public ProjectViewModel() {
        countriesListObservable = null;
        countryWeatherObservable = null;
    }

    public Call<CitiesWeatherModel> getCountriesListObservable() {
        countriesListObservable = getInstance().getWeatherForAllCountries(Constants.DEFAULT_COUNTRYCODES, Constants.API_KEY);
        return countriesListObservable;
    }

    public Call<WeatherModel> getWeatherByCountry(String countryID) {
        countryWeatherObservable = getInstance().getWeatherDataByCityCode(countryID, Constants.API_KEY);
        return countryWeatherObservable;
    }
}
