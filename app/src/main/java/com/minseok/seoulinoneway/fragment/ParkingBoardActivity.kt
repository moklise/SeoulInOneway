package com.minseok.seoulinoneway.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonObject
import com.minseok.seoulinoneway.R
import com.minseok.seoulinoneway.SeoulInOneWayApplication
import com.minseok.seoulinoneway.cards.ParkingCard
import com.minseok.seoulinoneway.cards.adapter.SubCardBoardAdapter
import com.minseok.seoulinoneway.common.ExtendedActivity
import com.minseok.seoulinoneway.common.LocationHelper
import com.minseok.seoulinoneway.network.Request
import com.minseok.seoulinoneway.network.RetrofitAPI
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ParkingBoardActivity  : ExtendedActivity(), OnMapReadyCallback {
    val mAdapter by lazy { SubCardBoardAdapter(this) }
    lateinit var mRecyclerView: RecyclerView
    var mSwipeRefreshLayout: SwipyRefreshLayout? = null

    var num_x=1
    var num_y=999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_parking_board)

        mRecyclerView = findViewById(R.id.list_parking_cards)


        mSwipeRefreshLayout = findViewById(R.id.swipe_layout)as SwipyRefreshLayout


        //새로고침시 newLoading
        mSwipeRefreshLayout!!.setOnRefreshListener(SwipyRefreshLayout.OnRefreshListener {
            newLoading()
            mSwipeRefreshLayout!!.setRefreshing(false)
        })

        //구글맵
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }//onCreate



    fun newLoading() {
        num_x +=999
        num_y +=999

        RetrofitAPI.getInstance().createService(Request::class.java)
                .getParkingInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("GetParkInfo").asJsonObject
                        val row = value.get("row").asJsonArray

                        val list = ArrayList<ParkingCard>()
                        for (item in row) {
                            item.asJsonObject.let {
                                ParkingCard().apply {
                                    this.parkingName = it["PARKING_NAME"].asString
                                    this.pakringAddress = it["ADDR"].asString
                                    this.parkingPayNM = it["PAY_NM"].asString

                                    this.parkingRates = it["RATES"].asString
                                    this.parkingTimeRate = it["TIME_RATE"].asString

                                    this.parkingAddRates = it["ADD_RATES"].asString
                                    this.parkingAddTimeRate = it["ADD_TIME_RATE"].asString

                                    this.parkingXCODE = it["LAT"].asString
                                    this.parkingYCODE = it["LNG"].asString
                                }.also {
                                    list.add(it)
                                }
                            }
                        }

                        mAdapter.notifyItemRangeInserted(mAdapter.itemCount - list.size, list.size)
                    }

                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                })

    }

    private fun initCardBoard(googleMap: GoogleMap)  {
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)


        RetrofitAPI.getInstance().createService(Request::class.java)
                .getParkingInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("GetParkInfo").asJsonObject
                        val row = value.get("row").asJsonArray

                        // 1. 데이터 넣어주기
                        val list = ArrayList<ParkingCard>()
                        for (item in row) {
                            item.asJsonObject.let {
                                ParkingCard().apply {
                                    this.parkingName = it["PARKING_NAME"].asString
                                    this.pakringAddress = it["ADDR"].asString
                                    this.parkingPayNM = it["PAY_NM"].asString

                                    this.parkingRates = it["RATES"].asString
                                    this.parkingTimeRate = it["TIME_RATE"].asString

                                    this.parkingAddRates = it["ADD_RATES"].asString
                                    this.parkingAddTimeRate = it["ADD_TIME_RATE"].asString

                                    this.parkingXCODE = it["LNG"].asString
                                    this.parkingYCODE = it["LAT"].asString
                                }.also {
                                    if (it.parkingXCODE.isNotEmpty() && it.parkingYCODE.isNotEmpty()) {
                                        list.add(it)

                                        val parking = LatLng(it.parkingYCODE.toDouble(), it.parkingXCODE.toDouble())
                                        googleMap.addMarker(MarkerOptions().position(parking)
                                                .title(it.parkingName))
                                    }
                                }

                            }
                        }

                        LocationHelper.checkCurrentLocation(this@ParkingBoardActivity, object: LocationHelper.LocationCallback{
                            override fun onSuccess(latitude: Double, longitude: Double) {
                                val convertedCurPosition =  Pair(longitude, latitude)

                                for (aItem in list) {
                                    val comparedPosition = Pair(aItem.parkingXCODE.toDouble(), aItem.parkingYCODE.toDouble())
                                    aItem.lengthFromCurrent = LocationHelper.getLengthBetweenLocations(convertedCurPosition, comparedPosition)
                                }

                                // 2. 데이터 리스트 정렬 (현재위치와의 거리로 정렬 오름차순)
                                Collections.sort(list, Comparator<ParkingCard> { p1, p2 ->
                                    val comparedPosition = p1.lengthFromCurrent
                                    val targetPosition = p2.lengthFromCurrent

                                    return@Comparator when {
                                        comparedPosition > targetPosition -> 1
                                        comparedPosition == targetPosition -> 0
                                        else -> -11
                                    }
                                })

                                mAdapter.addAll(list)
                                mAdapter.notifyDataSetChanged()

                                val setting = SeoulInOneWayApplication.getSettingManager()
                                val curPosition = LatLng(setting.latitude.toDouble(), setting.longitude.toDouble())
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPosition, 16.0f))
                            }

                            override fun onFailure() {
                                toast("위치 정보를 가져오는데 실패했습니다.")
                            }
                        })
                    }

                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    }
                })
        mAdapter.notifyDataSetChanged()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        initCardBoard(googleMap)
    }
}