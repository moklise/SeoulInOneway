package com.minseok.seoulinoneway.common

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by minseok on 2018. 9. 8..
 * SeoulInOneWay.
 */
object TimeHelper {

    fun isOver2PM(): Boolean {
        val time = getCal()
        Log.d("Calendar.Hour", ": " + time[Calendar.HOUR])
        return time[Calendar.HOUR_OF_DAY] >= 14
    }

    /* 20180908 */
    fun getTodayAsString(): String {
        val time = getCal().time
        val simpleDateFormat = SimpleDateFormat("YYYYMMdd")

        return simpleDateFormat.format(time)
    }

    fun getYesterdayAsString(): String {
        val yCal = getCal()
        yCal.add(Calendar.DATE, -1)
        val simpleDateFormat = SimpleDateFormat("YYYYMMdd")
        return simpleDateFormat.format(yCal.time)
    }

    private fun getCal(): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("GMT")).apply {
            this.timeZone = TimeZone.getTimeZone("Asia/Seoul")
        }
    }
}