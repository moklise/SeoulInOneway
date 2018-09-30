package com.minseok.seoulinoneway.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.JsonObject
import com.minseok.seoulinoneway.R
import com.minseok.seoulinoneway.cards.DrugstoreCard
import com.minseok.seoulinoneway.cards.adapter.MainCardBoardAdapter
import com.minseok.seoulinoneway.common.ExtendedActivity
import com.minseok.seoulinoneway.common.LocationHelper
import com.minseok.seoulinoneway.network.Request
import com.minseok.seoulinoneway.network.RetrofitAPI
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DrugstoreBoardActivity : ExtendedActivity() {
    val mAdapter by lazy { MainCardBoardAdapter(this) }
    lateinit var mRecyclerView: RecyclerView
    var mSwipeRefreshLayout: SwipyRefreshLayout? = null

    var num_x=1
    var num_y=999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_drugstore_board)

        mRecyclerView = findViewById(R.id.list_drugstore_cards)

        initCardBoard()


        mSwipeRefreshLayout = findViewById(R.id.swipe_layout)as SwipyRefreshLayout

        mSwipeRefreshLayout!!.setOnRefreshListener(SwipyRefreshLayout.OnRefreshListener {
            newLoading()
            mSwipeRefreshLayout!!.setRefreshing(false)
        })

    }


    //새로고침하면 30개씩보여줌
    fun newLoading() {
        num_x +=30
        num_y +=30

        RetrofitAPI.getInstance().createService(Request::class.java)
                .getDrugstoreInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("parmacyBizInfo").asJsonObject
                        val row = value.get("row").asJsonArray

                        for (item in row) {
                            item.asJsonObject.let {
                                DrugstoreCard().apply {
                                    // this: DrugstoreCard  it: JsonObject
                                    this.drugStoreName = it["NM"].asString
                                    this.drugStoreAddress = it["ADDR"].asString
                                    this.drugStoreTel = it["TEL"].asString
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

    private fun initCardBoard() {
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)


        RetrofitAPI.getInstance().createService(Request::class.java)
                .getDrugstoreInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("parmacyBizInfo").asJsonObject
                        val row = value.get("row").asJsonArray

                        // 1. 데이터 넣어주기
                        val list = ArrayList<DrugstoreCard>()
                        for (item in row) {
                            item.asJsonObject.let {
                                DrugstoreCard().apply {
                                    // this: DrugstoreCard  it: JsonOiject
                                    this.drugStoreName = it["NM"].asString
                                    this.drugStoreAddress = it["ADDR"].asString
                                    this.drugStoreTel = it["TEL"].asString

                                    this.drugStoreXCODE = it["XCODE"].asString.replace("%20d", "")
                                    this.drugStoreYCODE = it["YCODE"].asString.replace("%20d", "")

                                    list.add(this)
                                }
                            }
                        }

                        LocationHelper.checkCurrentLocation(this@DrugstoreBoardActivity, object: LocationHelper.LocationCallback{
                            override fun onSuccess(latitude: Double, longitude: Double) {

                                val curPosition = Pair(latitude, longitude)
                                RetrofitAPI.getInstance().createKakaoService(Request::class.java)
                                        .convertWWGS84toWTM(curPosition.first.toString(), curPosition.second.toString())
                                .enqueue(object: Callback<JsonObject> {
                                            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                                                val result = response?.body()
                                                val documents = result!!.get("documents").asJsonArray
                                                val position = documents.get(0).asJsonObject
                                                val posX = position.get("x").asString.toDouble()
                                                val posY = position.get("y").asString.toDouble()

                                                val convertedCurPosition = Pair(posX, posY)

                                                for (aItem in list) {
                                                    val comparedPosition = Pair(aItem.drugStoreXCODE.toDouble(), aItem.drugStoreYCODE.toDouble())
                                                    aItem.lengthFromCurrent = LocationHelper.getLengthBetweenLocations(convertedCurPosition, comparedPosition)
                                                }

                                                // 2. 데이터 리스트 정렬 (현재위치와의 거리로 정렬 오름차순)
                                                Collections.sort(list, Comparator<DrugstoreCard> { p1, p2 ->
                                                    val comparedPosition = p1.lengthFromCurrent
                                                    val targetPosition = p2.lengthFromCurrent

                                                    return@Comparator when {
                                                        comparedPosition > targetPosition -> -1
                                                        comparedPosition == targetPosition -> 0
                                                        else -> 1
                                                    }
                                                })

                                                mAdapter.addAll(list)
                                                mAdapter.notifyDataSetChanged()
                                            }

                                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                                    }
                                })
                            }

                            override fun onFailure() {
                                toast("위치 정보를 가져오는데 실패했습니다.")
                            }
                        })
                    }

                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    }
                })
    }
}