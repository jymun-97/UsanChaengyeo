package com.usanchaengyeo.usanchaengyeo.data.repository

import com.usanchaengyeo.usanchaengyeo.data.model.SearchResponse
import retrofit2.Response

interface AddressSearchRepository {

    suspend fun searchAddress(
        keyword: String
    ): Response<SearchResponse>
}