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
import com.minseok.seoulinoneway.cards.ToiletCard
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


class ToiletBoardActivity : ExtendedActivity(), OnMapReadyCallback {
    val mAdapter by lazy { SubCardBoardAdapter(this) }
    lateinit var mRecyclerView: RecyclerView
    var mSwipeRefreshLayout: SwipyRefreshLayout? = null

    var num_x = 2
    var num_y = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_toilet_board)

        mRecyclerView = findViewById(R.id.list_toilet_cards)



        mSwipeRefreshLayout = findViewById(R.id.swipe_layout) as SwipyRefreshLayout

        mSwipeRefreshLayout!!.setOnRefreshListener(SwipyRefreshLayout.OnRefreshListener {
            newLoading()
            mSwipeRefreshLayout!!.setRefreshing(false)
        })


        //구글맵
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }//onCreate 여기까지


    fun newLoading() {
        num_x += 999
        num_y += 999

        RetrofitAPI.getInstance().createService(Request::class.java)
                .getToiletInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("MgisToilet").asJsonObject
                        val row = value.get("row").asJsonArray

                        for (item in row) {
                            item.asJsonObject.let {
                                ToiletCard().apply {
                                    this.toiletName = it["COT_CONTS_NAME"].asString
                                    this.toiletAddress = it["COT_ADDR_FULL_OLD"].asString
                                    this.toiletTel = it["COT_TEL_NO"].asString

                                    this.toiletXCODE = it["COT_COORD_X"].asString
                                    this.toiletYCODE = it["COT_COORD_Y"].asString

                                }.also {
                                    mAdapter.add(it)
                                }
                            }
                        }

                        mAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                })

    }

    private fun initCardBoard(googleMap: GoogleMap) {
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        LocationHelper.checkCurrentLocation(this@ToiletBoardActivity, object : LocationHelper.LocationCallback {
            override fun onSuccess(latitude: Double, longitude: Double) {
                val curPosition = Pair(longitude, latitude)

                val list = ArrayList<ToiletCard>()

                RetrofitAPI.getInstance().createService(Request::class.java)
                        .getToiletInfo(num_x, num_y)
                        .enqueue(object : Callback<JsonObject> {
                            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                                val result = response!!.body()!!
                                val value = result.get("MgisToilet").asJsonObject
                                val row = value.get("row").asJsonArray

                                for (item in row) {
                                    item.asJsonObject.let {
                                        ToiletCard().apply {
                                            this.toiletName = it["COT_CONTS_NAME"].asString
                                            this.toiletAddress = it["COT_ADDR_FULL_OLD"].asString
                                            this.toiletTel = it["COT_TEL_NO"].asString

                                            this.toiletXCODE = it["COT_COORD_X"].asString
                                            this.toiletYCODE = it["COT_COORD_Y"].asString

                                        }.also {
                                            list.add(it)

                                            val toiletMap = LatLng(it.toiletYCODE.toDouble(), it.toiletXCODE.toDouble())
                                            googleMap.addMarker(MarkerOptions().position(toiletMap)
                                                    .title(it.toiletName))
                                        }
                                    }
                                }

                                for (aItem in list) {
                                    val comparedPosition = Pair(aItem.toiletXCODE.toDouble(), aItem.toiletYCODE.toDouble())
                                    aItem.lengthFromCurrent = LocationHelper.getLengthBetweenLocations(curPosition, comparedPosition)
                                }

                                Collections.sort(list, Comparator<ToiletCard> { p1, p2 ->
                                    return@Comparator when {
                                        p1.lengthFromCurrent > p2.lengthFromCurrent -> 1
                                        p1.lengthFromCurrent == p2.lengthFromCurrent -> 0
                                        else -> -1
                                    }
                                })

                                mAdapter.addAll(list)
                                mAdapter.notifyDataSetChanged()
                                val setting = SeoulInOneWayApplication.getSettingManager()
                                val curPosition = LatLng(setting.latitude.toDouble(), setting.longitude.toDouble())
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPosition, 16.0f))
                            }

                            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                        })
            }

            override fun onFailure() {
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        initCardBoard(googleMap)
    }
}

