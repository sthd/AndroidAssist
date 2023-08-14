package com.er.aa.weather;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WeatherViewModel extends ViewModel {

    private final MutableLiveData<WeatherInfo> weatherData = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<WeatherInfo> getWeatherData() {
        return weatherData;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchWeatherData(String apiUrl) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                String json = fetchJsonFromUrl(apiUrl);
                WeatherInfo weatherInfo = parseWeatherInfo(json);
                weatherData.postValue(weatherInfo);
            } catch (Exception e) {
                error.postValue("Error fetching weather data");
            }
        });
    }

    private String fetchJsonFromUrl(String apiUrl) throws Exception {
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            stringBuilder.append(line);
        }
        reader.close();
        connection.disconnect();
        return stringBuilder.toString();
    }

    private WeatherInfo parseWeatherInfo(String json) throws Exception {
        JSONObject jsonObject = new JSONObject(json);
        JSONObject location = jsonObject.getJSONObject("location");
        JSONObject current = jsonObject.getJSONObject("current");

        String locationName = location.getString("name");
        double temperatureC = current.getDouble("temp_c");
        String conditionText = current.getJSONObject("condition").getString("text");

        return new WeatherInfo(locationName, temperatureC, conditionText);
    }
}
