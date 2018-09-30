package com.minseok.seoulinoneway.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherRequest {
    // 날씨 (하늘 맑음 구름조금 이런거)
    @GET("ForecastSpaceData?ServiceKey=C50BsdyQkJj0XHlwD2gK%2Fd09kgoIVeEf%2Fnes0PKkoEi9H15RRo5qjPrhhpwe4TvA4UsQxMdCLcj1lQX9hJ3uVw%3D%3D&base_date=20180909&base_time=1400&nx=55&ny=127&pageNo=1&numOfRows=191&_type=json")
    Call<JsonObject> getWeatherInfo();
}
