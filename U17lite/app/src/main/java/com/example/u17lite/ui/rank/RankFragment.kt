package com.example.u17lite.ui.rank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.u17lite.ComicAdapter
import com.example.u17lite.GetRequest
import com.example.u17lite.MyApplication
import com.example.u17lite.R
import com.example.u17lite.ui.ComicItem
import com.scwang.smartrefresh.layout.api.RefreshLayout
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RankFragment : Fragment(){

    private lateinit var rankViewModel: RankViewModel
    private lateinit var adapter: ComicAdapter
    lateinit var refreshLayout : RefreshLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view1:View = inflater.inflate(R.layout.fragment_home,container,false)
        val layoutManager = LinearLayoutManager(activity)
        var page = 1
        view1.recyclerView.layoutManager = layoutManager
        adapter = ComicAdapter(view1.context)
        view1.recyclerView.adapter = adapter
        getData("1")
        refreshLayout = view1.RefreshLayout
        refreshLayout.setOnRefreshListener { refreshlayout ->
            refreshlayout.finishRefresh(1000 /*,false*/) //传入false表示刷新失败
            adapter.list.clear()
            getData("1")
        }
        refreshLayout.setOnLoadMoreListener { refreshlayout ->
            refreshlayout.finishLoadMore(1000 /*,false*/) //传入false表示加载失败
            if(page<8){
                page += 1
                getData(page.toString())
            }
            else Toast.makeText(MyApplication.getContext(),"到底了啦~",Toast.LENGTH_SHORT).show()
        }
        return view1
    }

    private fun getData(page : String){
        var retrofit = Retrofit.Builder()
            .baseUrl("http://app.u17.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        var service1 : GetRequest = retrofit.create(GetRequest::class.java)

        service1.getData1("total","2","xiaomi","7de42d2e","450010","MI+6","f5c9b6c9284551ad",page).enqueue(object : retrofit2.Callback<ComicItem> {

            override fun onResponse(call: Call<ComicItem>, response: Response<ComicItem>) {
                var comicitem : ComicItem? = response.body()
                if (comicitem != null) {
                    adapter.addData(comicitem)
                }
            }
            override fun onFailure(call: Call<ComicItem>, t: Throwable) {
                t.printStackTrace()
            }
            })
        }



}
