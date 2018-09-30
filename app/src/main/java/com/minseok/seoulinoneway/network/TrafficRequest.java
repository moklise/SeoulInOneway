package com.minseok.seoulinoneway.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TrafficRequest {
    // 지하철
    @GET("70734470707468653433654f4d6d44/json/realtimeStationArrival/0/5/{station_name}")
    Call<JsonObject> getSubwayInfo(@Path("station_name") String stationName);
}
