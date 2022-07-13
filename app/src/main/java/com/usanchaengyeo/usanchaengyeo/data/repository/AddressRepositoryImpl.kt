package com.usanchaengyeo.usanchaengyeo.data.repository

import com.usanchaengyeo.usanchaengyeo.data.db.SearchHistoryDatabase
import com.usanchaengyeo.usanchaengyeo.data.model.address.Address
import com.usanchaengyeo.usanchaengyeo.data.model.address.SearchResponse
import com.usanchaengyeo.usanchaengyeo.data.service.AddressSearchService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val addressSearchService: AddressSearchService,
    private val db: SearchHistoryDatabase
) : AddressRepository {

    override suspend fun searchAddress(keyword: String): Response<SearchResponse> =
        addressSearchService.searchAddress(keyword)

    override suspend fun addHistory(address: Address) = db.historyDao().insertHistory(address)

    override suspend fun deleteHistory(address: Address) = db.historyDao().deleteHistory(address)

    override fun loadHistory(): Flow<List<Address>> = db.historyDao().loadHistory()
}