package com.minseok.seoulinoneway.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by minseok on 2018. 7. 10..
 * SeoulInOneWay.
 */
public interface Request {
    // 날씨
    @GET("/json/RealtimeWeatherStation/1/50/")
    Call<JsonObject> getWeather1();

    //날씨 (최저,최고기온)
    @GET("/json/DailyWeatherStation/1/5/{date}/{city_name}")
    Call<JsonObject> getWeather2(@Path("date") String date, @Path("city_name") String cityName);

    //대기
    @GET("/json/ListAirQualityByDistrictService/1/5/{city_code}")
    Call<JsonObject> getAtmosphere(@Path("city_code") int cityCode);

    //날씨 현재구름많음적음 + 비 눈 진눈깨비 이런거 + 내일모레 최고온도최저온도
    @GET("ForecastSpaceData?ServiceKey=&base_time=1400&pageNo=1&numOfRows=160&_type=json")
    Call<JsonObject> getWeatherInfo(@Query("base_date") String date, @Query("nx") int x, @Query("ny") int y);

    //문화
    @GET("/json/SearchConcertDetailService/{num_x}/{num_y}")
    Call<JsonObject> getCultureInfo(@Path("num_x") int num_x, @Path("num_y") int num_y);

    //약국
    @GET("/json/parmacyBizInfo/{num_x}/{num_y}")
    Call<JsonObject> getDrugstoreInfo(@Path("num_x") int num_x, @Path("num_y") int num_y);

    //당구장
    @GET("/json/blrDrmInfo/{num_x}/{num_y}")
    Call<JsonObject> getBilliardInfo(@Path("num_x") int num_x, @Path("num_y") int num_y);

    //박물관
    @GET("/json/SebcMuseumInfoKor2/{num_x}/{num_y}")
    Call<JsonObject> getMuseumInfo (@Path("num_x") int num_x, @Path("num_y") int num_y);

    //화장실
    @GET("/json/MgisToilet/{num_x}/{num_y}")
    Call<JsonObject> getToiletInfo (@Path("num_x") int num_x, @Path("num_y") int num_y);

    //푸드트럭
    @GET("/json/foodTruckInfo/{num_x}/{num_y}")
    Call<JsonObject> getFoodtruckInfo (@Path("num_x") int num_x, @Path("num_y") int num_y);

    //주차장
    @GET("/json/GetParkInfo/{num_x}/{num_y}")
    Call<JsonObject> getParkingInfo (@Path("num_x") int num_x, @Path("num_y") int num_y);

    //전통시장
    @GET("/json/ListTraditionalMarket/{num_x}/{num_y}")
    Call<JsonObject> getTraditionalMarketInfo (@Path("num_x") int num_x, @Path("num_y") int num_y);


    /**
     * Kakao APIs
     */

    // 좌표변환 WTM to WGS84
    @GET("geo/transcoord.json?input_coord=WTM&output_coord=WGS84")
    Call<JsonObject> convertWTMtoWWGS84(@Query("x") String x, @Query("y") String y);

    // 좌표변환 WGS84 to WTM
    @GET("geo/transcoord.json?input_coord=WGS84&output_coord=WTM")
    Call<JsonObject> convertWWGS84toWTM(@Query("x") String x, @Query("y") String y);

    // 좌표 to 지역
    @GET("geo/coord2regioncode.json")
    Call<JsonObject> convertPositionToAddress(@Query("x") Double x, @Query("y") Double y);
}