package com.jymun.usanchaengyeo.data.service

import com.jymun.usanchaengyeo.data.entity.address.AddressName
import com.jymun.usanchaengyeo.data.entity.address.AddressResponse
import com.jymun.usanchaengyeo.data.service.NetworkConstant.ADDRESS_SERVICE_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AddressService {

    @Headers("Authorization: KakaoAK $ADDRESS_SERVICE_KEY")
    @GET("/v2/local/geo/coord2address")
    suspend fun coordinateToAddressName(
        @Query("x") longitude: Double,
        @Query("y") latitude: Double
    ): Response<AddressName>

    @Headers("Authorization: KakaoAK $ADDRESS_SERVICE_KEY")
    @GET("/v2/local/search/keyword")
    suspend fun searchAddressByKeyword(
        @Query("query") keyword: String
    ): Response<AddressResponse>
}