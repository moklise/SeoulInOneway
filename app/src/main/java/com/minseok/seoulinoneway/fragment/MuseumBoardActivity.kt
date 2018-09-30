package com.minseok.seoulinoneway.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.gson.JsonObject
import com.minseok.seoulinoneway.R
import com.minseok.seoulinoneway.cards.MuseumCard
import com.minseok.seoulinoneway.cards.adapter.SubCardBoardAdapter
import com.minseok.seoulinoneway.common.ExtendedActivity
import com.minseok.seoulinoneway.network.Request
import com.minseok.seoulinoneway.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* 읽어주세여!!읽어주세여!!읽어주세여!!읽어주세여!!읽어주세여!!읽어주세여!!읽어주세여!!읽어주세여!! */
/**
 * 검색 기능을 몰래 추가해놓았습니다 ㅎㅎ
 * 이에 따라 추가되는 점이 있습니다
 *
 * 먼저, 모아보기용 어댑터인 SubCardBoardAdapter를 만들었습니다!
 * 모아보기에 있는 전통시장, 푸드트럭 등 이 친구들은 MainCardBoardAdapter 대신 이 친구를 사용해주세요!
 *
 * MainCardBoardAdapter와 다르게 모아보기에 있는 친구들을 위한 메소드를 몇개 추가해놓았습니다.
 * 관련된 설명은 추가된 메소드 위에 적어놓았으니 반드시 확인해주세요!
 */
class MuseumBoardActivity  : ExtendedActivity() {
    val mAdapter by lazy { SubCardBoardAdapter(this) }
    lateinit var mRecyclerView: RecyclerView

//    lateinit var editTextMuseumSearch : EditText
//    lateinit var btnMuseumSearch : Button
    lateinit var btnGCODESearch : Button
    lateinit var tvGCODEviewing : TextView

//    var mSwipeRefreshLayout: SwipeRefreshLayout? = null

    var num_x=1
    var num_y=500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_museum_board)

//        editTextMuseumSearch = findViewById(R.id.edittext_museum_search)
//        btnMuseumSearch = findViewById(R.id.btn_museum_search)


        tvGCODEviewing = findViewById(R.id.tvGCODE_viewing)

        btnGCODESearch = findViewById(R.id.btn_GCODE_search)
        registerForContextMenu(btnGCODESearch)


        mRecyclerView = findViewById(R.id.list_museum_cards)

        initCardBoard()



//swipeRefresh 필요없어서 안쓰기로 함
//        mSwipeRefreshLayout = findViewById(R.id.swipe_layout)as SwipeRefreshLayout

//        mSwipeRefreshLayout!!.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
//            mSwipeRefreshLayout!!.setRefreshing(false)



//            editTextMuseumSearch.addTextChangedListener(object : TextWatcher {
//                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//                    // 입력하기 전에
//                }
//
//                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//                    // 입력되는 텍스트에 변화가 있을 때
//                    val text = s.toString()
//
////                    if (text.length != 0) {
////                        btnMuseumSearch.setVisibility(View.VISIBLE)
////                    } else {
////                        btnMuseumSearch.setVisibility(View.GONE)
////                    }
//
//
//                }
//
//                override fun afterTextChanged(s: Editable) {
//                    // 입력이 끝난후에
//                }
//            })//박물관 검색 여기까지
    }

    //자치구 선택하는 메뉴
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val mInflater = menuInflater
        if (v == btnGCODESearch) {
            menu!!.setHeaderTitle("자치구 설정")
            mInflater.inflate(R.menu.menu1, menu)
        }
    }

    //선택됐을때의 반응 when
    @Override
    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.GCODE1 -> "종로구"
            R.id.GCODE2 -> "용산구"
            R.id.GCODE3 -> "광진구"
            R.id.GCODE4 -> "중랑구"
            R.id.GCODE5 -> "강북구"
            R.id.GCODE6 -> "노원구"
            R.id.GCODE7 -> "서대문구"
            R.id.GCODE8 -> "양천구"
            R.id.GCODE9 -> "구로구"
            R.id.GCODE10 -> "영등포구"
            R.id.GCODE11 -> "관악구"
            R.id.GCODE12 -> "강남구"
            R.id.GCODE13 -> "강동구"
            R.id.GCODE14 -> "중구"
            R.id.GCODE15 -> "성동구"
            R.id.GCODE16 -> "동대문구"
            R.id.GCODE17 -> "성북구"
            R.id.GCODE18 -> "도봉구"
            R.id.GCODE19 -> "은평구"
            R.id.GCODE20 -> "마포구"
            R.id.GCODE21 -> "강서구"
            R.id.GCODE22 -> "금천구"
            R.id.GCODE23 -> "동작구"
            R.id.GCODE24 -> "서초구"
            R.id.GCODE25 -> "송파구"
            else -> "종로구"
        }.also {
            filterList(it)
            tvGCODEviewing.text = it
        }
        return true
    }

    private fun filterList(location: String) {
        mAdapter.findAllWith(location)
    }




    private fun initCardBoard() {
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        RetrofitAPI.getInstance().createService(Request::class.java)
                .getMuseumInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result.get("SebcMuseumInfoKor2").asJsonObject
                        val row = value.get("row").asJsonArray

                        for (item in row) {
                            item.asJsonObject.let {
                                MuseumCard().apply {
                                    this.museumName = it["NAME_KOR"].asString
                                    this.museumAddress = it["H_KOR_GU"].asString
                                    this.museumKeyword = it["NM_DP"].asString
                                }.also {
                                    mAdapter.add(it)
                                }
                            }
                        }

                        mAdapter.notifyDataSetChanged()
                        mAdapter.initOriginal()
                    }

                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                })
    }
}

