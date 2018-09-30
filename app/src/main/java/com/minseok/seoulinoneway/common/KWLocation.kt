package com.minseok.seoulinoneway.common

import org.jetbrains.annotations.NotNull
import kotlin.math.*

/**
 * Created by minseok on 2018. 8. 29..
 * SeoulInOneWay.
 *
 * 기상청 동네날씨정보.
 *
 * 좌표 (X,Y) -> 위경도
 * 위경도 -> 좌표 (X,Y)
 */
class KWLocation {
    private val NX = 149 // X축 격자점 수
    private val NY = 253 // Y축 격자점 수

    companion object {
        private var INSTANCE: KWLocation? = null

        fun getInstance(): KWLocation {
            if (INSTANCE == null) {
                INSTANCE = KWLocation()
            }

            return INSTANCE!!
        }
    }

    fun convertToLocation(@NotNull x: Float , @NotNull y: Float): Pair<Float, Float> {
        val pair = convertPositionToLocation(x - 1, y - 1, MapInfo).let {
            Pair(it.first, it.second)
        }

        if (x < 1 || x > NX || y < 1 || y > NY) {
            throw IllegalArgumentException("좌표 값이 범위를 벗어났습니다.")
        }

        print("X = ${x.toInt()}, Y = ${y.toInt()}  --->lon.= ${pair.first}, lat.= ${pair.second}\n")
        return pair
    }

    fun convertToPosition(@NotNull longitude: Number , @NotNull latitude: Number): Pair<Float, Float> {
        val pair = convertLocationToPosition(longitude.toFloat(), latitude.toFloat(), MapInfo).let {
            Pair(it.first + 1.5F, it.second + 1.5F)
        }

        print("lon.= ${longitude}, lat.= ${latitude} ---> X = ${pair.first}, Y = ${pair.second}\n")
        return pair
    }

    /* 위경도 -> (X,Y) */
    private fun convertLocationToPosition(lon: Float, lat: Float, map: MapInfo): Pair<Float, Float> {
        val PIf = PI.toFloat()
        val DEGRAD = PIf / 180.0F

        var re = map.Re / map.grid
        var slat1 = map.slat1 * DEGRAD
        var slat2 = map.slat2 * DEGRAD
        var olon = map.olon * DEGRAD
        var olat = map.olat * DEGRAD

        var sn = tan(PIf * 0.25F + slat2 * 0.5F) / tan(PIf * 0.25F + slat1 * 0.5F)
        sn = log(cos(slat1) / cos(slat2), 10F) / log(sn, 10F)

        var sf = tan(PIf * 0.25F + slat1 * 0.5F)
        sf = sf.pow(sn) * cos(slat1) / sn

        var ro = tan(PIf * 0.25F + olat * 0.5F)
        ro = (re * sf / ro.pow(sn))



        var ra = tan(PIf * 0.25F + lat * DEGRAD * 0.5F).let {
            re * sf / it.pow(sn)
        }

        var theta = lon * DEGRAD - olon

        if (theta > PIf) theta -= 2.0F * PIf
        if (theta < -PIf) theta += 2.0F * PIf
        theta *= sn

        var newX = (ra * sin(theta)) + map.xo
        var newY = (ro - ra * cos(theta)) + map.yo

        return Pair(newX, newY)
    }


    /* (X,Y) -> 위경도 */
    private fun convertPositionToLocation(x: Float, y: Float, map: MapInfo): Pair<Float, Float> {
        val PIf = PI.toFloat()
        val DEGRAD = PIf / 180.0F
        val RADDEG = 180.0F / PIf

        var re = map.Re / map.grid
        var slat1 = map.slat1 * DEGRAD
        var slat2 = map.slat2 * DEGRAD
        var olon = map.olon * DEGRAD
        var olat = map.olat * DEGRAD

        var sn = tan(PIf * 0.25F + slat2 * 0.5F) / tan(PIf * 0.25F + slat1 * 0.5F)
        sn = log(cos(slat1) / cos(slat2), 10F) / log(sn, 10F)

        var sf = tan(PIf * 0.25F + slat1 * 0.5F)
        sf = sf.pow(sn) * cos(slat1) / sn

        var ro = tan(PIf * 0.25F + olat * 0.5F)
        ro = (re * sf / ro.pow(sn))

        var ra = 0F
        var theta = 0F
        var xn = 0F
        var yn = 0F
        var alat = 0F
        var alon = 0F
        var newLon = 0F
        var newLat = 0F

        xn = x - map.xo
        yn = ro - y + map.yo
        ra = kotlin.math.sqrt(xn * xn + yn * yn)

        if (sn < 0.0F) ra = -ra

        alat = (re * sf / ra).pow((1.0F / sn))
        alat = 2.0F * atan(alat) - PIf * 0.5F

        if (kotlin.math.abs(xn) <= 0.0F) {
            theta = 0.0F

        } else {
            if (kotlin.math.abs(yn) <= 0.0F) {
                theta = PIf * 0.5F

                if (xn < 0.0F) theta = -theta
            } else {
                theta = atan2(xn, yn)
            }
        }

        alon = theta / sn + olon
        newLon = alon * RADDEG
        newLat = alat * RADDEG

        return Pair(newLon, newLat)
    }

    object MapInfo {
        val Re = 6371.00877F     // 사용할 지구반경
        val grid = 5.0F          // 격자간격
        val slat1 = 30.0F        // 표준위도
        val slat2 = 60.0F        // 표준 위도
        val olon = 126.0F        // 기준점의 경도 [degree]
        val olat = 38.0F         // 기준점의 위도 [degree]
        val xo = 210F / this.grid  // 기준점의 X좌표 [degree]
        val yo = 675F / this.grid  // 기준점의 Y좌표 [degree]
    }

    enum class TYPE {
        POSITION,
        LOCATION
    }
}