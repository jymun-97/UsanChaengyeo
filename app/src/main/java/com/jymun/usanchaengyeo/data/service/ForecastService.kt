package com.jymun.usanchaengyeo.data.service

import com.jymun.usanchaengyeo.data.entity.forecast.ForecastResponse
import com.jymun.usanchaengyeo.data.service.NetworkConstant.FORECAST_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastService {

    @GET("/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?ServiceKey=$FORECAST_API_KEY")
    suspend fun runForecast(
        @Query("pageNo") page: Int = 1,
        @Query("numOfRows") numOfRows: Int = 1000,
        @Query("dataType") dataType: String = "JSON",
        @Query("base_date") date: String,
        @Query("base_time") time: String,
        @Query("nx") x: Int,
        @Query("ny") y: Int
    ): Response<ForecastResponse>
}