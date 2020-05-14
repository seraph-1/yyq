package com.example.u17lite

import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.ManhuaDaoOpe
import com.bumptech.glide.Glide
import com.example.u17lite.MyApplication.read_way
import com.example.u17lite.ui.Chapter
import com.example.u17lite.ui.ComicChapter
import com.example.u17lite.ui.ComicItem
import kotlinx.android.synthetic.main.activity_detial.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DetialActivity : AppCompatActivity() {

    lateinit var adapter: ChapterAdapter
    var list = ArrayList<Chapter>()
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detial)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun initView(){
        var name : String? = intent.getStringExtra("name")
        var cover : String? = intent.getStringExtra("cover")
        var tag : String? = intent.getStringExtra("tag")
        var detial : String? = intent.getStringExtra("detial")
        var comicid : String? = intent.getStringExtra("id")
        var isCollect :Boolean = false

        Glide.with(this).load(cover).into(detial_pic)
        detial_title.text = name
        detial_tag.text = tag
        detial_detial.text = detial
        val layoutManager = LinearLayoutManager(this)
        isCollect = ManhuaDaoOpe.getInstance().queryCollectForName(MyApplication.getContext(),name!!)?.size !=0
        if(isCollect){
            detial_collect.text = "已收藏"
        }
        else detial_collect.text = "收藏"
        detial_collect.setOnClickListener(View.OnClickListener {
            if (isCollect){
                isCollect = !isCollect
                detial_collect.text = "收藏"
                ManhuaDaoOpe.getInstance().deleteCollectData(MyApplication.getContext(),
                    Manhua(name,cover,tag,detial,comicid,"",false,false)
                )
                Toast.makeText(MyApplication.getContext(),"已取消收藏",Toast.LENGTH_SHORT).show()
            }
            else {
                isCollect = !isCollect
                detial_collect.text = "已收藏"
                ManhuaDaoOpe.getInstance().insertCollectData(MyApplication.getContext(),Manhua(name,cover,tag,detial,comicid,"",true,false))
                Toast.makeText(MyApplication.getContext(),"已收藏",Toast.LENGTH_SHORT).show()
            }
        })
        chapter_recyclerView.layoutManager = layoutManager
        getChapterData(comicid!!)
        adapter = ChapterAdapter(this,Manhua(name,cover,tag,detial,comicid,"",isCollect,false))
        chapter_recyclerView.adapter = adapter
        if(read_way == 1){
            read_horizontal.background = getDrawable(R.drawable.edge3)
            read_vertical.background = getDrawable(R.drawable.edge2)
        }
        else if (read_way == 0){
            read_horizontal.background = getDrawable(R.drawable.edge2)
            read_vertical.background = getDrawable(R.drawable.edge3)
        }
        read_vertical.setOnClickListener(View.OnClickListener {
            read_way = 0
            read_horizontal.background = getDrawable(R.drawable.edge2)
            read_vertical.background = getDrawable(R.drawable.edge3)
            Toast.makeText(MyApplication.getContext(),"已经选择纵向阅读!",Toast.LENGTH_SHORT).show()
        })
        read_horizontal.setOnClickListener(View.OnClickListener {
            read_way = 1
            read_horizontal.background = getDrawable(R.drawable.edge3)
            read_vertical.background = getDrawable(R.drawable.edge2)
            Toast.makeText(MyApplication.getContext(),"已经选择横向阅读!",Toast.LENGTH_SHORT).show()
        })
    }

    private fun getChapterData(comicid : String){
        var retrofit = Retrofit.Builder()
            .baseUrl("http://app.u17.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        var service1 : GetRequest = retrofit.create(GetRequest::class.java)
        service1.getData3("xiaomi","7de42d2e","450010","MI+6","f5c9b6c9284551ad",comicid).enqueue( object : retrofit2.Callback<ComicChapter>{
            override fun onResponse(call: Call<ComicChapter>, response: Response<ComicChapter>) {
                var chapter : ComicChapter? = response.body()
                if (chapter!= null) {
                    adapter.addData(chapter)

                }

            }
            override fun onFailure(call: Call<ComicChapter>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }
}
