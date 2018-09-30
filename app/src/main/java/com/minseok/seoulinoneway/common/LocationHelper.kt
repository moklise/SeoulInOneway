package com.minseok.seoulinoneway.common

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.minseok.seoulinoneway.SeoulInOneWayApplication

/**
 * Created by minseok on 2018. 8. 26..
 * SeoulInOneWay.
 *
 * 위치와 관련된 모든 액션
 */
object LocationHelper {

    private var fusedLocationClient: FusedLocationProviderClient? = null

    fun checkCurrentLocation(mContext: Context, callback: LocationCallback) {
        getLastLocation(mContext, callback)
    }

    /**
     * 실제 데이터를 위치를 받는 부분
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation(mContext: Context, callback: LocationCallback) {

        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext)
        }

        fusedLocationClient!!.lastLocation
                .addOnCompleteListener { task ->
                    if (task.isSuccessful && task.result != null) {
                        val latitude = task.result.latitude
                        val longitude = task.result.longitude

                        Log.d("LocationInformation", "lat: $latitude, lon: $longitude")
                        SeoulInOneWayApplication.getSettingManager().apply {
                            this.setLatitude(latitude.toFloat())
                            this.setLongitude(longitude.toFloat())
                        }

                        callback.onSuccess(latitude, longitude)
                    } else {
                        callback.onFailure()
                    }
                }
    }

    fun getNearestStation(latitude: Double, longitude: Double): String {
        val list: List<StationLocation> = LocationStationProvider.getStationLocationList()

        var shortestLength = Double.MAX_VALUE
        var shortestStation: StationLocation? = null;
        for (station in list) {
            val thisLength = station.getLength(latitude, longitude)

            if (shortestLength >= thisLength) {
                shortestLength = thisLength
                shortestStation = station
            }
        }

        return shortestStation?.name ?: "둔촌동"
    }

    public fun getLengthBetweenLocations(location1: Pair<Double, Double>, location2: Pair<Double, Double>): Double {
        var elem1 = location2.second - location1.second
        var elem2 = location2.first - location1.first

        return Math.sqrt(Math.pow(elem1, 2.0) + Math.pow(elem2, 2.0))
    }

    interface LocationCallback {
        fun onSuccess(latitude: Double, longitude: Double)
        fun onFailure();
    }
}
