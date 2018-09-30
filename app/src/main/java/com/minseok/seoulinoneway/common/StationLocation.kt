package com.minseok.seoulinoneway.common

/**
 * Created by minseok on 2018. 9. 16..
 * SeoulInOneWay.
 */
data class StationLocation(
        val name: String,
        private val latitude: Double,
        private val longitude: Double) {

    fun getLength(latitude: Double, longitude: Double): Double {
        val elem1 = this.latitude - latitude
        val elem2 = this.longitude - longitude

        return Math.sqrt(Math.pow(elem1, 2.0) + Math.pow(elem2, 2.0))
    }
}