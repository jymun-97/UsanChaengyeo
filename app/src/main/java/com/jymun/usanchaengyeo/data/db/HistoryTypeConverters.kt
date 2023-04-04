package com.jymun.usanchaengyeo.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.jymun.usanchaengyeo.data.entity.address.AddressEntity

class HistoryTypeConverters {

    @TypeConverter
    fun addressEntityToString(value: AddressEntity?): String? = Gson().toJson(value)

    @TypeConverter
    fun stringToAddressEntity(value: String?): AddressEntity? =
        Gson().fromJson(value, AddressEntity::class.java)
}