package com.example.u17lite.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.u17lite.ComicAdapter
import com.example.u17lite.GetRequest
import com.example.u17lite.R
import com.example.u17lite.ui.ComicItem
import kotlinx.android.synthetic.main.fragment_search.view.recyclerview
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    lateinit var adapter : ComicAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_search,container,false)
        val layoutManager = LinearLayoutManager(activity)
        view.recyclerview.layoutManager = layoutManager
        adapter = ComicAdapter(view.context)
        view.recyclerview.adapter = adapter
        var editText : EditText? = view.findViewById(R.id.search_text)
        var button : Button? = view.findViewById(R.id.search_button1)
        button?.setOnClickListener {
            adapter.list.clear()
            var keyword : String = editText?.text.toString()
            getSearchData(keyword)
        }
        return view
    }



    private fun getSearchData(keyword : String){
        var retrofit = Retrofit.Builder()
            .baseUrl("http://app.u17.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        var service1 : GetRequest = retrofit.create(GetRequest::class.java)
        service1.getData2("xiaomi","7de42d2e","4500102","MI+6","f5c9b6c9284551ad",keyword).enqueue(object : retrofit2.Callback<ComicItem> {

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
