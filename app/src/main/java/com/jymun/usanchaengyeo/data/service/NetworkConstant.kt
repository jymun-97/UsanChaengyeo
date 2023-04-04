package com.jymun.usanchaengyeo.data.service

import com.jymun.usanchaengyeo.BuildConfig

object NetworkConstant {

    const val ADDRESS_BASE_URL = "https://dapi.kakao.com"
    const val ADDRESS_SERVICE_KEY = BuildConfig.address_service_key

    const val FORECAST_BASE_URL = "http://apis.data.go.kr"
    const val FORECAST_API_KEY = BuildConfig.forecast_api_key
}