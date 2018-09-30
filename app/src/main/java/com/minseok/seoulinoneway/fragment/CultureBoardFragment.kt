package com.minseok.seoulinoneway.fragment

import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import com.minseok.seoulinoneway.R
import com.minseok.seoulinoneway.cards.Card
import com.minseok.seoulinoneway.cards.CultureCard
import com.minseok.seoulinoneway.cards.adapter.SubCardBoardAdapter
import com.minseok.seoulinoneway.network.Request
import com.minseok.seoulinoneway.network.RetrofitAPI
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CultureBoardFragment : Fragment() {
    var mContainer: CoordinatorLayout? = null
    val mAdapter by lazy { SubCardBoardAdapter(activity) }
    lateinit var mRecyclerView: RecyclerView
    var mSwipeRefreshLayout: SwipyRefreshLayout? = null

    lateinit var btnGCODESearch : ImageButton
    lateinit var tvGCODEviewing : TextView

    var num_x = 1         //밑에서 쓰임 getCultureInfo에서 num_x는 1번부터
    var num_y = 10        //num_y 10번까지 불러오기. 점점 숫자 늘릴 예정

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (mContainer == null) {
            mContainer = (inflater.inflate(R.layout.content_culture_board, null, false) as CoordinatorLayout?)!!


            tvGCODEviewing = mContainer!!.findViewById(R.id.tvGCODE_viewing)
            btnGCODESearch = mContainer!!.findViewById(R.id.btn_GCODE_search)
            registerForContextMenu(btnGCODESearch)

            mRecyclerView = mContainer!!.findViewById(R.id.list_culture_cards)
        }

        tvGCODEviewing = mContainer!!.findViewById(R.id.tvGCODE_viewing)

        btnGCODESearch = mContainer!!.findViewById(R.id.btn_GCODE_search)
        registerForContextMenu(btnGCODESearch)

        initCultureContainer()

        //onCreateView안에 써주고
        mSwipeRefreshLayout = mContainer!!.findViewById(R.id.swipe_layout) as SwipyRefreshLayout

        //또 onCreateView안에다가  public void onRefresh() 해줘야함 근데 코틀린으로 썼더니 public void 이런건 안보임
        //만약 새로고침하면 newLoading 이라는 함수를 실행시킬거임 뉴로딩 선언은 바로밑에
        mSwipeRefreshLayout!!.setOnRefreshListener({
            newLoading()
            mSwipeRefreshLayout!!.setRefreshing(false)
        })

        return mContainer
    }


    //자치구 선택하는 메뉴
    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val mInflater = activity!!.menuInflater
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

    fun newLoading() {

        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)

        num_x += 10
        num_y += 10

        //다시 리퀘스트를 보내야한다고 함
        RetrofitAPI.getInstance().createService(Request::class.java)
                .getCultureInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result["SearchConcertDetailService"].asJsonObject
                        val row = value["row"].asJsonArray


                        val list = ArrayList<CultureCard>()
                        for (item in row) {
                            item.asJsonObject.let {
                                CultureCard().apply {
                                    // this: CultureCard  it: JsonObject
                                    this.title = it["TITLE"].asString
                                    this.image = it["MAIN_IMG"].asString
                                    this.startDate = it["STRTDATE"].asString
                                    this.endDate = it["END_DATE"].asString
                                    this.place = it["PLACE"].asString
                                    this.gCode = it["GCODE"].asString
                                    this.link = it["ORG_LINK"].asString
                                }.also {
                                    // it: CultureCard
//                                    mAdapter.add(it)
                                    list.add(it)
                                }
                            }
                        }

                        mAdapter.updateOriginal(list as List<Card>?)
                        mAdapter.notifyItemRangeInserted(mAdapter.itemCount - 10, list.size)
                        mAdapter.findAllWith(tvGCODEviewing.text as String?)
                    }
                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                })

    }

    private fun initCultureContainer() {
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        RetrofitAPI.getInstance().createService(Request::class.java)
                .getCultureInfo(num_x, num_y)
                .enqueue(object : Callback<JsonObject> {
                    override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                        val result = response!!.body()!!
                        val value = result["SearchConcertDetailService"].asJsonObject
                        val row = value["row"].asJsonArray

                        for (item in row) {
                            item.asJsonObject.let {
                                CultureCard().apply {
                                    // this: CultureCard  it: JsonObject
                                    this.title = it["TITLE"].asString
                                    this.image = it["MAIN_IMG"].asString
                                    this.startDate = it["STRTDATE"].asString
                                    this.endDate = it["END_DATE"].asString
                                    this.place = it["PLACE"].asString
                                    this.gCode = it["GCODE"].asString
                                    this.link = it["ORG_LINK"].asString
                                }.also {
                                    // it: CultureCard
                                    mAdapter.add(it)
                                }
                            }
                        }
                        mAdapter.notifyDataSetChanged()
                        mAdapter.initOriginal()
                    }

                    override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {}
                })
        Toast.makeText(activity, "목록을 불러왔습니다.", Toast.LENGTH_SHORT).show()

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = CultureBoardFragment()
    }
}
