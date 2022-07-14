package com.usanchaengyeo.usanchaengyeo.util

import com.usanchaengyeo.usanchaengyeo.BuildConfig
import com.usanchaengyeo.usanchaengyeo.data.model.forecast.Info

object Const {
    const val KAKAO_BASE_URL = "https://dapi.kakao.com"
    const val KAKAO_API_KEY = BuildConfig.KAKAO_API_KEY

    const val FORECAST_BASE_URL = "http://apis.data.go.kr"
    const val FORECAST_API_KEY = BuildConfig.FORECAST_API_KEY

    val INFO_LIST = listOf(
        Info(" 0", " 1", "비가 아예 내리지 않거나 체감을 못느끼는 정도", "우산을 굳이 챙기지 않아도 괜찮아요!"),
        Info(" 2", " 4", "육안으로 식별되나 물웅덩이는 생기지 않는 정도", "우산을 챙기면 편할거에요!"),
        Info(" 5", " 9", "거세진 빗줄기가 확실히 보이고 물웅덩이가 생기는 정도", "우산이 꼭 필요해요!"),
        Info("10", "20", "우비를 입어도 옷이 젖기 쉽고 물웅덩이가 꽤 크게 생기는 정도", "우산을 써도 옷이 젖을 수 있어요!"),
        Info("20", "30", "우산이나 우비가 소용없고 시야 확보가 어려운 정도", "우산을 써도 소용없을 정도로 비가 올거에요! "),
        Info("30", "  ", "물통으로 퍼붓는 느낌이며 호우주의보에 가까운 정도", "호우주의보가 내려질 수 있어요!")
    )
}