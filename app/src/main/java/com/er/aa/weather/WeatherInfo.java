package com.er.aa.weather;

public class WeatherInfo {
    private String locationName;
    private double temperatureC;
    private String conditionText;

    public WeatherInfo(String locationName, double temperatureC, String conditionText) {
        this.locationName = locationName;
        this.temperatureC = temperatureC;
        this.conditionText = conditionText;
    }

    public String getLocationName() {
        return locationName;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public String getConditionText() {
        return conditionText;
    }
}
