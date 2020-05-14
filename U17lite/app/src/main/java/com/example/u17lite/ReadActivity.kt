package com.example.u17lite

import android.icu.lang.UCharacter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.u17lite.MyApplication.read_way
import com.example.u17lite.ui.ComicImage
import com.example.u17lite.ui.Image
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.fragment_collect.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ReadActivity : AppCompatActivity() {

    var imageList : ArrayList<Image> = ArrayList()
    var chapterList : ArrayList<String> = ArrayList()
    lateinit var chapterId : String
    var sign = true
    lateinit var name : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read)
        chapterId = intent.getStringExtra("chapterid")
        chapterList = intent.getStringArrayListExtra("chapteridlist")
        if(chapterId != null)getImageData(chapterId)
        if(read_way == 0)
            readpager.orientation = ViewPager2.ORIENTATION_VERTICAL
        else if(read_way == 1)
            readpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        readpager.offscreenPageLimit = 3
        readpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
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
    }

    private fun initViewPager(){
        readpager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return imageList.size
            }

            override fun createFragment(position: Int): Fragment {
                if(chapterList != null){
                    if(position == imageList.size-3){
                        var nextChapter = chapterList.indexOf(chapterId) + 1
                        if (nextChapter != chapterList.size){
                            chapterId = chapterList[nextChapter]
                            getImageData(chapterId)
                        }
                    }
                }
                return ReadFragment(imageList[position].location)
            }
        }
    }

    private fun getImageData(chapterId : String){
        var retrofit = Retrofit.Builder()
            .baseUrl("http://app.u17.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        var service1 : GetRequest = retrofit.create(GetRequest::class.java)
        service1.getData4("shouji360","4BAFC35ED2160879","5000101","ALP-AL00",chapterId,"98674364a00c73c2").enqueue(object : retrofit2.Callback<ComicImage>{
            override fun onResponse(call: Call<ComicImage>, response: Response<ComicImage>) {
                var image : ComicImage? = response.body()
                if(image != null) {
                    imageList.addAll(image.data.returnData.image_list)
                    if(sign){
                        sign = false
                        initViewPager()
                    }
                }
            }

            override fun onFailure(call: Call<ComicImage>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}
