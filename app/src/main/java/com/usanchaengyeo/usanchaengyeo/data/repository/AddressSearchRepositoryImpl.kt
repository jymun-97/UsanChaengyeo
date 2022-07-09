package com.usanchaengyeo.usanchaengyeo.data.repository

import com.usanchaengyeo.usanchaengyeo.data.model.SearchResponse
import com.usanchaengyeo.usanchaengyeo.data.service.AddressSearchService
import retrofit2.Response
import javax.inject.Inject

class AddressSearchRepositoryImpl @Inject constructor(
    private val addressSearchService: AddressSearchService
) : AddressSearchRepository {

    override suspend fun searchAddress(keyword: String): Response<SearchResponse> =
        addressSearchService.searchAddress(keyword)
}