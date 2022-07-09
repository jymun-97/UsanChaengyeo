package com.usanchaengyeo.usanchaengyeo.data.service

import com.usanchaengyeo.usanchaengyeo.data.model.SearchResponse
import com.usanchaengyeo.usanchaengyeo.util.Const.KAKAO_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AddressSearchService {

    @Headers("Authorization: KakaoAK $KAKAO_API_KEY")
    @GET("/v2/local/search/keyword")
    suspend fun searchAddress(
        @Query("query") keyword: String
    ): Response<SearchResponse>
}