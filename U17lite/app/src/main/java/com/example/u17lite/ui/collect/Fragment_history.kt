package com.example.u17lite.ui.collect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ManhuaDaoOpe
import com.example.u17lite.MyApplication
import com.example.u17lite.R
import kotlinx.android.synthetic.main.fragment_viewpage_collect.view.*
import kotlinx.android.synthetic.main.fragment_viewpage_history.*
import kotlinx.android.synthetic.main.fragment_viewpage_history.view.*
import kotlinx.android.synthetic.main.fragment_viewpage_history.view.fab_history

class Fragment_history : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view : View = inflater.inflate(R.layout.fragment_viewpage_history,container,false)
        var list = ManhuaDaoOpe.getInstance().queryAllHistory(MyApplication.getContext())
        var times = 0
        view.history_back.setImageResource(R.mipmap.nobook)
        view.yes_history.visibility = View.GONE
        view.no_history.visibility = View.GONE
        if(list != null && list.size != 0){
            view.history_back.visibility = View.GONE
            var adapter = HistoryAdapter(MyApplication.getContext())
            val layoutmanager = LinearLayoutManager(activity)
            view.history_recyclerview.layoutManager = layoutmanager
            view.history_recyclerview.adapter = adapter
            adapter.setData(list)
            view.yes_history.setOnClickListener(View.OnClickListener {
                adapter.yessignal()
                adapter.notifyDataSetChanged()
            })
            view.no_history.setOnClickListener(View.OnClickListener {
                adapter.nosignal()
                view.yes_history.visibility = View.GONE
                view.no_history.visibility = View.GONE
                times = 0
                adapter.sign = false
                adapter.notifyDataSetChanged()
            })
            view.fab_history.setOnClickListener(View.OnClickListener {
                times++
                adapter.clear()
                adapter.sign = true
                Log.d("10086","开始工作")
                view.yes_history.visibility = View.VISIBLE
                view.no_history.visibility = View.VISIBLE
                adapter.notifyDataSetChanged()
                if(times == 2){
                    view.yes_history.visibility = View.GONE
                    view.no_history.visibility = View.GONE
                    times = 0
                    adapter.sign = false
                }
                adapter.notifyDataSetChanged()
            })
        }
        return view
    }
}