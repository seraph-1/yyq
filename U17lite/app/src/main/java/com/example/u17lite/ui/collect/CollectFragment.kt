package com.example.u17lite.ui.collect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.u17lite.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_collect.view.*

class CollectFragment : Fragment() {

    private lateinit var collectViewModel: CollectViewModel
    var list : ArrayList<Fragment> = ArrayList()
    lateinit var Fragment_collect : Fragment
    lateinit var Fragment_history : Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_collect,container,false)

        view.view_pager.offscreenPageLimit = 3
        Fragment_history = Fragment_history()
        Fragment_collect = Fragment_collect()
        list.add(Fragment_collect)
        list.add(Fragment_history)
        view.view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
        initViewPager(view)
        initTabViewPager(view)
        return view
    }



    private fun initViewPager(view : View){
        view.view_pager.adapter = object : FragmentStateAdapter(this){
            override fun getItemCount(): Int {
                return list.size
            }

            override fun createFragment(position: Int): Fragment {
                return list[position]
            }
        }
    }

    private fun initTabViewPager(view : View) {
        TabLayoutMediator(view.tab_layout, view.view_pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                if (position == 0) tab.text = "收藏"
                else tab.text = "历史"
            }
        ).attach()
    }



}
