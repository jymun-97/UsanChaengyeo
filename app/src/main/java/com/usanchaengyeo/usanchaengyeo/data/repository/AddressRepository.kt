package com.usanchaengyeo.usanchaengyeo.data.repository

import com.usanchaengyeo.usanchaengyeo.data.model.address.Address
import com.usanchaengyeo.usanchaengyeo.data.model.address.SearchResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AddressRepository {

    suspend fun searchAddress(
        keyword: String
    ): Response<SearchResponse>

    suspend fun addHistory(address: Address)

    suspend fun deleteHistory(address: Address)

    fun loadHistory(): Flow<List<Address>>
}