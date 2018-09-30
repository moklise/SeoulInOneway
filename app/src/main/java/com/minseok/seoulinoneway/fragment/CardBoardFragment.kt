package com.minseok.seoulinoneway.fragment

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.minseok.seoulinoneway.MainCardBoardActivity
import com.minseok.seoulinoneway.R
import com.minseok.seoulinoneway.SeoulInOneWayApplication
import com.minseok.seoulinoneway.cards.AtmosphereCard
import com.minseok.seoulinoneway.cards.SimpleTextCard
import com.minseok.seoulinoneway.cards.SubwayCard
import com.minseok.seoulinoneway.cards.WeatherCard
import com.minseok.seoulinoneway.cards.adapter.MainCardBoardAdapter
import com.minseok.seoulinoneway.cards.model.AtmosphereLocationMapper
import com.minseok.seoulinoneway.cards.model.SubwayMapper
import com.minseok.seoulinoneway.common.ExtendedFragment
import com.minseok.seoulinoneway.common.KWLocation
import com.minseok.seoulinoneway.common.LocationHelper
import com.minseok.seoulinoneway.common.TimeHelper
import com.minseok.seoulinoneway.network.Request
import com.minseok.seoulinoneway.network.RetrofitAPI
import com.minseok.seoulinoneway.network.TrafficRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardBoardFragment : ExtendedFragment() {
    var mContainer: CoordinatorLayout? = null
    val mAdapter by lazy { MainCardBoardAdapter(activity) }
    val setting by lazy { SeoulInOneWayApplication.getSettingManager() }
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (mContainer == null) {
            mContainer = inflater.inflate(R.layout.fragment_card_board, container, false) as CoordinatorLayout

            mRecyclerView = mContainer!!.findViewById(R.id.list_main_cards)
        }

        initRealPosition()

        return mContainer
    }


   private fun initRealPosition() {
        LocationHelper.checkCurrentLocation(this.context!!, object : LocationHelper.LocationCallback {
            override fun onSuccess(latitude: Double, longitude: Double) {

                RetrofitAPI.getInstance().createKakaoService(Request::class.java)
                        .convertPositionToAddress(longitude, latitude)
                        .enqueue(object: Callback<JsonObject> {
                            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                                val body = response?.body()
                                val addressList = body?.get("documents")?.asJsonArray ?: JsonArray()
                                val oldAddressJson = addressList[0].asJsonObject
                                val rawRegionName = oldAddressJson.get("region_2depth_name").asString

                                val regions = rawRegionName.split(" ")

                                val targetRegionName = when { // 구 이름의 최소 길이는 2글자. 다른 구의 경우 "구" 가 생략되지만 중구 같은 경우 "중구"로 표기됨
                                    regions.last().length <= 2 -> regions.last()
                                    else -> regions.last().substring(0 until regions.last().lastIndex)
                                }

                                setting.regionName = targetRegionName

                                initCardBoard()
                            }

                            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }
                        })
            }

            override fun onFailure() {
                toast("위치 정보를 불러올 수 없습니다.")
            }
        })
    }

    private fun initCardBoard() {
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)

//        날씨카드
        WeatherCard().let {
            RetrofitAPI.getInstance().createService(Request::class.java)
                    .weather1
                    .enqueue(object : Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                            val result = response!!.body()!!
                            val value = result.get("RealtimeWeatherStation").asJsonObject
                            val row = value.get("row").asJsonArray

                            val tempRegion = when (setting.regionName) {
                                "종로" -> "남산"
                                "금천" -> "관악"
                                "강서" -> "양천"
                                else -> setting.regionName
                            }

                            val targetRegion = row.find { it.asJsonObject["STN_NM"].asString == tempRegion }?.asJsonObject
                                    ?: JsonObject()
                            val cityTemperature = targetRegion.get("SAWS_TA_AVG").asString
                            val rainSum = targetRegion.get("SAWS_RN_SUM").asString //강수량

//                            it.location = cityName
                            it.location = setting.regionName
                            it.temperature = cityTemperature.toFloat().toInt()
                            it.rainSum = rainSum.toFloat().toInt()


                            // 날씨는 매일 오후 2시에 새로 발표됨. 2시 이전엔 어제의 기록을 사용해야 함.
                            val date = if (TimeHelper.isOver2PM()) {
                                TimeHelper.getTodayAsString()
                            } else {
                                TimeHelper.getYesterdayAsString()
                            }

                            // 최고기온 최저기온
                            /* API가 정확한 데이터 보여주지않을 가능성은 무수히 많음. */
                            RetrofitAPI.getInstance().createService(Request::class.java)
                                    .getWeather2(date, setting.regionName)
                                    .enqueue(object : Callback<JsonObject> {
                                        override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                                            val result = response!!.body()!!

                                            if (result.has("DailyWeatherStation")) {
                                                val value = result.get("DailyWeatherStation").asJsonObject
                                                val row = value.get("row").asJsonArray
                                                val weatherInfo = row.get(0).asJsonObject
                                                val lowestTemp = weatherInfo.get("SAWS_TA_MIN").asString
                                                val highestTemp = weatherInfo.get("SAWS_TA_MAX").asString

                                                it.lowestTemp = lowestTemp.toFloat().toInt() //string->소수->int
                                                it.highestTemp = highestTemp.toFloat().toInt()

                                                mAdapter.notifyDataSetChanged()
                                            } else {
                                                it.lowestTemp = 999
                                                it.highestTemp = 999
                                            }
                                        }

                                        override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                                        }
                                    })


                            val kwLocationPair = KWLocation.getInstance()
                                    .convertToPosition(setting.longitude, setting.latitude)

                            RetrofitAPI.getInstance().createWeatherService(Request::class.java)
                                    .getWeatherInfo(date, kwLocationPair.first.toInt(), kwLocationPair.second.toInt())
                                    .enqueue(object : Callback<JsonObject> {
                                        override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                                            val result = response!!.body()!!
                                            val value = result.get("response").asJsonObject

                                            if (value.has("body")) {

                                                val body = value.get("body").asJsonObject
                                                val items = body.get("items").asJsonObject
                                                val item = items.get("item").asJsonArray
                                                val skyValue = item.get(5).asJsonObject
                                                val PTYValue = item.get(1).asJsonObject

                                                val tomorrowLowTemp = item.get(47).asJsonObject
                                                val tomorrowHighTemp = item.get(77).asJsonObject
                                                val dayAfterTomorrowLowTemp = item.get(129).asJsonObject
                                                val dayAfterTomorrowHighTemp = item.get(159).asJsonObject

                                                val itemSkyValue = skyValue.get("fcstValue").asString
                                                val itemPTYvalue = PTYValue.get("fcstValue").asString

                                                val item_tomorrowLowTemp = tomorrowLowTemp.get("fcstValue").asString
                                                val item_tomorrowHighTemp = tomorrowHighTemp.get("fcstValue").asString
                                                val item_dayAfterTomorrowLowTemp = dayAfterTomorrowLowTemp.get("fcstValue").asString
                                                val item_dayAfterTomorrowHighTemp = dayAfterTomorrowHighTemp.get("fcstValue").asString

                                                it.skyValue = itemSkyValue.toFloat().toInt()
                                                it.PTYValue = itemPTYvalue.toFloat().toInt()

                                                it.tomorrow_lowTemp = item_tomorrowLowTemp
                                                it.tomorrow_highTemp = item_tomorrowHighTemp
                                                it.dayAfterTomorrow_lowTemp = item_dayAfterTomorrowLowTemp
                                                it.dayAfterTomorrow_highTemp = item_dayAfterTomorrowHighTemp

                                                mAdapter.notifyDataSetChanged()
                                            }
                                        }

                                        override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                                    })
                        }

                        override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                        }
                    })

            it.onClick = View.OnClickListener {}
            mAdapter.add(it)
        }


        // 대기카드
        AtmosphereCard().let {
            val code = AtmosphereLocationMapper.nameToCode(setting.regionName)
            RetrofitAPI.getInstance().createService(Request::class.java)
                    .getAtmosphere(code)
                    .enqueue(object : Callback<JsonObject> {
                        override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {

                            val result = response!!.body()!!
                            val value = result.get("ListAirQualityByDistrictService").asJsonObject
                            val row = value.get("row").asJsonArray
                            val atmosphereInfo = row.get(0).asJsonObject

                            val cityName = atmosphereInfo.get("MSRSTENAME").asString
                            val MAXINDEX = atmosphereInfo.get("MAXINDEX").asString
                            val PM10 = atmosphereInfo.get("PM10").asString
                            val PM25 = atmosphereInfo.get("PM25").asString

                            it.location = cityName
                            it.MaxIndex = MAXINDEX.toFloat().toInt()
//                            it.MaxIndex = 14 //테스트값

                            it.PM10 = PM10
                            it.PM25 = PM25
                        }

                        override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                        }
                    })
            mAdapter.add(it)
        }

        LocationHelper.checkCurrentLocation(this.context!!, object : LocationHelper.LocationCallback {
            override fun onSuccess(latitude: Double, longitude: Double) {

                Log.d("Location", "" + latitude + ", " + longitude)
                val stationName = LocationHelper.getNearestStation(latitude, longitude)

                SubwayCard().let {
                    try {
                        RetrofitAPI.getInstance().createTrafficService(TrafficRequest::class.java)
                                .getSubwayInfo(stationName)
                                .enqueue(object : Callback<JsonObject> {
                                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                                        val result = when (response?.code()) {
                                            200 -> response.body()!!
//                                            else -> JsonParser().parse(jsonRaw).asJsonObject
                                            else -> return
                                        }
                                        val trainList = result["realtimeArrivalList"].asJsonArray

                                        SubwayMapper.inject(it, trainList)
                                        mAdapter.add(it)
                                        mAdapter.notifyDataSetChanged()
                                    }

                                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                                })
                    } catch (e: Exception) {
//                            val result = JsonParser().parse(jsonRaw).asJsonObject
//                            val trainList = result["realtimeArrivalList"].asJsonArray
//
//                            SubwayMapper.inject(it, trainList)
//                            mAdapter.add(it)
//                            mAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure() {
                toast("위치 정보를 가져올 수 없습니다.")
            }
        })

        SimpleTextCard().apply {
            this.text = "오늘은 어떤 행사가 있는지 한번 확인해 보세요~!"
            this.onclick = View.OnClickListener {
                MainCardBoardActivity.sBottomNavigation.selectedItemId = R.id.action_culture
            }
        }.also { mAdapter.add(it) }
//
//        SampleCard().apply {
//            this.content = "읭 뭐야이거"
//        }.also { mAdapter.add(it) }
//
//        SampleCard().apply {
//            this.content = "읭 뭐야이거"
//        }.also { mAdapter.add(it) }
//
//        SampleCard().apply {
//            this.content = "읭 뭐야이거"
//        }.also { mAdapter.add(it) }

        mAdapter.notifyDataSetChanged()
    }


    companion object {
        @JvmStatic
        fun newInstance() = CardBoardFragment()
    }

    val jsonRaw = """
    {
    "errorMessage":
    {
        "status": 200,
        "code": "INFO-000",
        "message": "정상 처리되었습니다.",
        "link": "",
        "developerMessage": "",
        "total": 4
    },
    "realtimeArrivalList":
    [
        {
            "beginRow": null,
            "endRow": null,
            "curPage": null,
            "pageRow": null,
            "totalCount": 4,
            "rowNum": 1,
            "selectedCount": 4,
            "subwayId": "1005",
            "subwayNm": null,
            "updnLine": "상행",
            "trainLineNm": "방화행 - 강동방면",
            "subwayHeading": "오른쪽",
            "statnFid": "1005000548",
            "statnTid": "1005080550",
            "statnId": "1005080549",
            "statnNm": "둔촌동",
            "trainCo": null,
            "ordkey": "01005방화0",
            "subwayList": "1005",
            "statnList": "1005080549",
            "btrainSttus": null,
            "barvlDt": "600",
            "btrainNo": "5614",
            "bstatnId": "50",
            "bstatnNm": "방화",
            "recptnDt": "2018-08-24 15:48:53.0",
            "arvlMsg2": "10분 후 (거여)",
            "arvlMsg3": "거여",
            "arvlCd": "99"
        },
        {
            "beginRow": null,
            "endRow": null,
            "curPage": null,
            "pageRow": null,
            "totalCount": 4,
            "rowNum": 2,
            "selectedCount": 4,
            "subwayId": "1005",
            "subwayNm": null,
            "updnLine": "상행",
            "trainLineNm": "방화행 - 강동방면",
            "subwayHeading": "오른쪽",
            "statnFid": "1005000548",
            "statnTid": "1005080550",
            "statnId": "1005080549",
            "statnNm": "둔촌동",
            "trainCo": null,
            "ordkey": "02006방화",
            "subwayList": "1005",
            "statnList": "1005080549",
            "btrainSttus": null,
            "barvlDt": "1440",
            "btrainNo": "5616",
            "bstatnId": "51",
            "bstatnNm": "방화",
            "recptnDt": "2018-08-24 15:48:53.0",
            "arvlMsg2": "24분 후 (마천)",
            "arvlMsg3": "마천",
            "arvlCd": "99"
        },
        {
            "beginRow": null,
            "endRow": null,
            "curPage": null,
            "pageRow": null,
            "totalCount": 4,
            "rowNum": 3,
            "selectedCount": 4,
            "subwayId": "1005",
            "subwayNm": null,
            "updnLine": "하행",
            "trainLineNm": "마천행 - 올림픽공원방면",
            "subwayHeading": "왼쪽",
            "statnFid": "1005000548",
            "statnTid": "1005080550",
            "statnId": "1005080549",
            "statnNm": "둔촌동",
            "trainCo": null,
            "ordkey": "11007마천0",
            "subwayList": "1005",
            "statnList": "1005080549",
            "btrainSttus": null,
            "barvlDt": "240",
            "btrainNo": "5631",
            "bstatnId": "38",
            "bstatnNm": "마천",
            "recptnDt": "2018-08-24 15:49:03.0",
            "arvlMsg2": "4분 후 (천호(풍납토성))",
            "arvlMsg3": "천호(풍납토성)",
            "arvlCd": "99"
        },
        {
            "beginRow": null,
            "endRow": null,
            "curPage": null,
            "pageRow": null,
            "totalCount": 4,
            "rowNum": 4,
            "selectedCount": 4,
            "subwayId": "1005",
            "subwayNm": null,
            "updnLine": "하행",
            "trainLineNm": "마천행 - 올림픽공원방면",
            "subwayHeading": "왼쪽",
            "statnFid": "1005000548",
            "statnTid": "1005080550",
            "statnId": "1005080549",
            "statnNm": "둔촌동",
            "trainCo": null,
            "ordkey": "12013마천0",
            "subwayList": "1005",
            "statnList": "1005080549",
            "btrainSttus": null,
            "barvlDt": "1080",
            "btrainNo": "5633",
            "bstatnId": "32",
            "bstatnNm": "마천",
            "recptnDt": "2018-08-24 15:49:03.0",
            "arvlMsg2": "18분 후 (마장)",
            "arvlMsg3": "마장",
            "arvlCd": "99"
        }
    ]
}"""
}
