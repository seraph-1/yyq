package com.example.u17lite.ui.collect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ManhuaDaoOpe
import com.bumptech.glide.Glide
import com.example.u17lite.MyApplication
import com.example.u17lite.R
import kotlinx.android.synthetic.main.collect_item.view.*
import kotlinx.android.synthetic.main.fragment_viewpage_collect.*
import kotlinx.android.synthetic.main.fragment_viewpage_collect.view.*
import kotlinx.android.synthetic.main.fragment_viewpage_history.view.*

class Fragment_collect : Fragment() {
    var time = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_viewpage_collect,container,false)
        var list = ManhuaDaoOpe.getInstance().queryAllCollect(MyApplication.getContext())
        view.collect_back.setImageResource(R.mipmap.nobook)
        view.yes_collect.visibility = View.GONE
        view.no_collect.visibility = View.GONE
        if(list != null && list.size != 0){
            var adapter = CollectAdapter(MyApplication.getContext(),list)
            view.grid_view.adapter = adapter
            view.collect_back.visibility = View.GONE
            view.yes_collect.setOnClickListener(View.OnClickListener {
                adapter.yessignal()
                adapter.notifyDataSetChanged()
            })
            view.no_collect.setOnClickListener(View.OnClickListener {
                adapter.nosignal()
                view.yes_collect.visibility = View.GONE
                view.no_collect.visibility = View.GONE
                time = 0
                adapter.sign1 = false
            })
            view.fab_collect.setOnClickListener(View.OnClickListener {
                time++
                adapter.sign1 = true
                Log.d("10086","开始工作")
                view.yes_collect.visibility = View.VISIBLE
                view.no_collect.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
                if(time == 2){
                    view.yes_collect.visibility = View.GONE
                    view.no_collect.visibility = View.GONE
                    time = 0
                    adapter.sign1 = false
                }
                adapter.notifyDataSetChanged()
            })
        }
        return view
    }
}