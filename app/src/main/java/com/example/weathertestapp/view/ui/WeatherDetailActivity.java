package com.example.weathertestapp.view.ui;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.weathertestapp.R;
import com.example.weathertestapp.base.BaseActivity;
import com.example.weathertestapp.base.WeatherApplication;
import com.example.weathertestapp.service.API.WeatherService;
import com.example.weathertestapp.service.models.WeatherModel;
import com.example.weathertestapp.utils.Constants;
import com.example.weathertestapp.viewmodels.ProjectViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDetailActivity extends BaseActivity {

    TextView tv_cityname, tv_temp, tv_climate_description;
    ImageView weather_icon;
    ProjectViewModel viewModel;
    private String TAG = "WeatherDetailActivity";
    private WeatherService restInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);
        viewModel =
                ViewModelProviders.of(this).get(ProjectViewModel.class);
        tv_cityname = findViewById(R.id.tv_cityname);
        tv_temp = findViewById(R.id.tv_temp);
        tv_climate_description = findViewById(R.id.tv_climate_description);
        weather_icon = findViewById(R.id.weather_icon);

        WeatherApplication weatherApp = (WeatherApplication) getApplication();
        restInterface = WeatherApplication.initRestClient();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String countryName = extras.getString("countryName");
            Log.d(TAG, "Country Name " + countryName);
            tv_cityname.setText(countryName);
            if (super.isNetworkAvailable()) {
                loadCityData(countryName);
            } else {
                Toast.makeText(this, "Internet is not connected", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public void loadCityData(String countryName) {
        viewModel.getWeatherByCountry(countryName).enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(@NonNull Call<WeatherModel> call, @NonNull Response<WeatherModel> response) {
                generateUI(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<WeatherModel> call, @NonNull Throwable t) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void generateUI(WeatherModel weatherModel) {
        tv_temp.setText(weatherModel.getMain().getTemp() + "");
        tv_climate_description.setText(weatherModel.getWeather()[0].getMain() + ", " + weatherModel.getWeather()[0].getDescription());
        loadImageFromUrlToImageView(weather_icon, weatherModel.getWeather()[0].getIcon());
    }

    private void loadImageFromUrlToImageView(final ImageView imageView, String imageIconName) {

        if (imageIconName != null) {
            String imageUri = Constants.IMAGE_URL + imageIconName + ".png";
            Log.d(TAG, "ImageURL" + imageUri);

            // To load the image dynamically on-fly
            Glide.with(getApplicationContext()).load(imageUri)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageView);

        } else {
            Log.e(TAG, "Image ICON NOT FOUND");
        }
    }
}
