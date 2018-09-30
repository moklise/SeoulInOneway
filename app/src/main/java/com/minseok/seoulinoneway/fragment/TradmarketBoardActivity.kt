package com.minseok.seoulinoneway.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import com.google.gson.JsonObject
import com.minseok.seoulinoneway.R
import com.minseok.seoulinoneway.cards.DetailBoardWebView
import com.minseok.seoulinoneway.cards.TradMarketCard
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

class TradmarketBoardActivity : ExtendedActivity() {
    val mAdapter by lazy { MainCardBoardAdapter(this) }
    lateinit var mRecyclerView: RecyclerView
    var mSwipeRefreshLayout: SwipyRefreshLayout? = null

    lateinit var tvTradMarketWeb : TextView


    var num_x=1
    var num_y=999

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_market_board)

        mRecyclerView = findViewById(R.id.list_market_cards)
        tvTradMarketWeb = findViewById(R.id.txt_tradmarket_web)

        initCardBoard()


        mSwipeRefreshLayout = findViewById(R.id.swipe_layout)as SwipyRefreshLayout

        mSwipeRefreshLayout!!.setOnRefreshListener(SwipyRefreshLayout.OnRefreshListener {
            newLoading()
            mSwipeRefreshLayout!!.setRefreshing(false)
        })



        //타이틀밑에 텍뷰 클릭시 intent 이동
        tvTradMarketWeb.setOnClickListener {
            val intent = Intent(it.getContext(), DetailBoardWebView::class.java)
            startActivity(intent)
        }


    }



    fun newLoading() {
        num_x +=10
        num_y +=10

        RetrofitAPI.getInstance().createService(Request::class.java)
                .getTraditionalMarketInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("ListTraditionalMarket").asJsonObject
                        val row = value.get("row").asJsonArray

                        for (item in row) {
                            item.asJsonObject.let {
                                TradMarketCard().apply {
                                    this.marketName = it["M_NAME"].asString
                                    this.marketAddress = it["M_ADDR"].asString
                                    this.marketCity = it["GUNAME"].asString

                                    this.marketXCODE = it["LAT"].asString
                                    this.marketYCODE = it["LNG"].asString
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

        val list = ArrayList<TradMarketCard>()
        LocationHelper.checkCurrentLocation(this, object: LocationHelper.LocationCallback {
            override fun onSuccess(latitude: Double, longitude: Double) {
                RetrofitAPI.getInstance().createService(Request::class.java)
                        .getTraditionalMarketInfo(num_x, num_y)
                        .enqueue(object : Callback<JsonObject> {
                            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                                val result = response!!.body()!!
                                val value = result.get("ListTraditionalMarket").asJsonObject
                                val row = value.get("row").asJsonArray

                                for (item in row) {
                                    item.asJsonObject.let {
                                        TradMarketCard().apply {
                                            this.marketName = it["M_NAME"].asString
                                            this.marketAddress = it["M_ADDR"].asString
                                            this.marketCity = it["GUNAME"].asString

                                            this.marketXCODE = it["LAT"].asString
                                            this.marketYCODE = it["LNG"].asString
                                        }.also {
                                            list.add(it)
                                        }
                                    }
                                }

                                val curPosition = Pair(longitude, latitude)
                                for (item in list) {
                                    val comparedPosition = Pair(item.marketXCODE.toDouble(), item.marketYCODE.toDouble())
                                    item.lengthFromCurrent = LocationHelper.getLengthBetweenLocations(curPosition, comparedPosition)
                                }

                                Collections.sort(list, Comparator<TradMarketCard>() { p1, p2 ->
                                    return@Comparator when {
                                        p1.lengthFromCurrent > p2.lengthFromCurrent -> 1
                                        p1.lengthFromCurrent == p2.lengthFromCurrent -> 0
                                        else -> -1
                                    }
                                })

                                mAdapter.addAll(list)
                                mAdapter.notifyDataSetChanged()
                            }

                            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                        })
            }

            override fun onFailure() {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })


        mAdapter.notifyDataSetChanged()
    }
}

