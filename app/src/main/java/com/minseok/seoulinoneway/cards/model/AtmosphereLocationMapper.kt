package com.minseok.seoulinoneway.cards.model

/**
 * Created by minseok on 2018. 8. 31..
 * SeoulInOneWay.
 */
object AtmosphereLocationMapper {
    fun nameToCode(name: String) = getMap().filter { it.value.contains(name) }.minBy { it.value.length }?.key ?: 0

    fun codeToName(code: Int) = getMap()[code]

    private fun getMap(): HashMap<Int, String> {
        return HashMap<Int, String>().apply {
            this[111123] = "종로구"
            this[111121] = "중구"
            this[111131] = "용산구"
            this[111142] = "성동구"
            this[111141] = "광진구"
            this[111152] = "동대문구"
            this[111151] = "중랑구"
            this[111161] = "성북구"
            this[111291] = "강북구"
            this[111171] = "도봉구"
            this[111311] = "노원구"
            this[111181] = "은평구"
            this[111191] = "서대문구"
            this[111201] = "마포구"
            this[111301] = "양천구"
            this[111212] = "강서구"
            this[111221] = "구로구"
            this[111281] = "금천구"
            this[111231] = "영등포구"
            this[111241] = "동작구"
            this[111251] = "관악구"
            this[111262] = "서초구"
            this[111261] = "강남구"
            this[111273] = "송파구"
            this[111274] = "강동구"
        }
    }
}