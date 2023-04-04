package com.jymun.usanchaengyeo.util.forecast

import kotlin.math.*

object CoordinatesConverter {
    private val RE = 6371.00877     // 지구 반경(km)
    private val GRID = 5.0          // 격자 간격(km)
    private val SLAT1 = 30.0        // 투영 위도1(degree)
    private val SLAT2 = 60.0        // 투영 위도2(degree)
    private val OLON = 126.0        // 기준점 경도(degree)
    private val OLAT = 38.0         // 기준점 위도(degree)
    private val XO = 43             // 기준점 X좌표(GRID)
    private val YO = 136            // 기준점 Y좌표(GRID)
    private val DEGRAD = Math.PI / 180.0
    private val re = RE / GRID
    private val slat1 = SLAT1 * DEGRAD
    private val slat2 = SLAT2 * DEGRAD
    private val olon = OLON * DEGRAD
    private val olat = OLAT * DEGRAD

    private val sn =
        ln(cos(slat1) / cos(slat2)) / ln(tan(Math.PI * 0.25 + slat2 * 0.5) / tan(Math.PI * 0.25 + slat1 * 0.5))
    private val sf = tan(Math.PI * 0.25 + slat1 * 0.5).pow(sn) * cos(slat1) / sn
    private val ro = re * sf / tan(Math.PI * 0.25 + olat * 0.5).pow(sn)

    fun convert(latitude: Double, longitude: Double): Pair<Int, Int> {
        var ra = tan(Math.PI * 0.25 + (longitude) * DEGRAD * 0.5)
        ra = re * sf / ra.pow(sn)

        var theta = latitude * DEGRAD - olon

        if (theta > Math.PI) theta -= 2.0 * Math.PI
        if (theta < -Math.PI) theta += 2.0 * Math.PI
        theta *= sn

        val x = (ra * sin(theta) + XO + 0.5).toInt()
        val y = (ro - ra * cos(theta) + YO + 0.5).toInt()

        return Pair(x, y)
    }
}