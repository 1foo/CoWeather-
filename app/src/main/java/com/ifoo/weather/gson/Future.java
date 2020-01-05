package com.ifoo.weather.gson;

import java.lang.ref.SoftReference;

public class Future {
        private String temp;
        private String wind;
        private String week;
        private String weather;

    public Future(String temp, String wind, String week,String weather){
        this.temp = temp;
        this.wind = wind;
        this.week = week;
        this.weather =weather;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
