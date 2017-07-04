package edu.cpp.l05_http_basics;

import edu.cpp.l05_http_basics.data.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by yusun on 7/3/17.
 */

public interface WeatherService {

    @GET("/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1")
    public Call<WeatherResponse> getWeatherData();
}
