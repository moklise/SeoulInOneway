package com.minseok.seoulinoneway.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.JsonObject
import com.minseok.seoulinoneway.R
import com.minseok.seoulinoneway.cards.BilliardCard
import com.minseok.seoulinoneway.cards.adapter.MainCardBoardAdapter
import com.minseok.seoulinoneway.common.ExtendedActivity
import com.minseok.seoulinoneway.network.Request
import com.minseok.seoulinoneway.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BilliardBoardActivity  : ExtendedActivity() {
    val mAdapter by lazy { MainCardBoardAdapter(this) }
    lateinit var mRecyclerView: RecyclerView
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    var num_x=1
    var num_y=30

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_billiard_board)

        mRecyclerView = findViewById(R.id.list_billiard_cards)

        initCardBoard()


        mSwipeRefreshLayout = findViewById(R.id.swipe_layout)as SwipeRefreshLayout

        mSwipeRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            newLoading()
            mSwipeRefreshLayout!!.setRefreshing(false)
        })

    }


    //새로고침하면 10개씩보여줌
    fun newLoading() {
        num_x +=30
        num_y +=30

        RetrofitAPI.getInstance().createService(Request::class.java)
                .getBilliardInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("blrDrmInfo").asJsonObject
                        val row = value.get("row").asJsonArray

                        for (item in row) {
                            item.asJsonObject.let {
                                BilliardCard().apply {
                                    this.billiardName = it["NM"].asString
                                    this.billiardAddress = it["ADDR"].asString
                                    this.billiardTel = it["TEL"].asString
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
                .getBilliardInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("blrDrmInfo").asJsonObject
                        val row = value.get("row").asJsonArray

                        for (item in row) {
                            item.asJsonObject.let {
                                BilliardCard().apply {
                                    this.billiardName = it["NM"].asString
                                    this.billiardAddress = it["ADDR"].asString
                                    this.billiardTel = it["TEL"].asString

                                    this.billiardXCODE = it["XCODE"].asString
                                    this.billiardYCODE = it["YCODE"].asString



                                }.also {
                                    mAdapter.add(it)
                                }
                            }
                        }

                        mAdapter.notifyDataSetChanged()
                    }

                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                })

        mAdapter.notifyDataSetChanged()
    }
}

