package com.example.u17lite.ui.collect

import android.content.Context
import android.content.Intent
import android.util.Log

import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ManhuaDaoOpe
import com.bumptech.glide.Glide
import com.example.u17lite.*
import com.example.u17lite.ui.ComicChapter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HistoryAdapter(private val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var list : MutableList<Manhua> = ArrayList()
    var chapterList : ArrayList<String> = ArrayList()
    var positionList : ArrayList<Int> = ArrayList()
    var deleteList : MutableList<Manhua> = ArrayList()
    var sign = false
    var deletesign = 0
    var flag1 = 0

    fun clear(){
        positionList.clear()
        deleteList.clear()
    }

    fun setFlag(flag1 : Int){
        this.flag1 = flag1
    }

    fun getFlag() : Int{
        return flag1
    }

    fun setData(list : MutableList<Manhua>){
        this.list = list
        notifyDataSetChanged()
    }

    fun setsign(sign : Boolean) {
        this.sign = sign
    }

    fun getsign() : Boolean {
        return sign
    }

    fun yessignal() {
            for (i in deleteList){
                ManhuaDaoOpe.getInstance()
                    .deleteHistoryData(MyApplication.getContext(), i)
                removeData(i)
            }
        positionList.clear()
        deleteList.clear()
        deletesign = -1

    }

    fun nosignal() {
        deletesign = 2
        if (deletesign == 2){
            deleteList.clear()
            positionList.clear()
            deletesign = -1
        }
    }

    fun removeData(position: Manhua){
        list.remove(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = View.inflate(context, R.layout.comic_item,null)
        return ComicAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ComicAdapter.ViewHolder ->{
                holder.textView_title.text = list[position].name
                holder.textview_tag.text = list[position].tag
                holder.textview_detial.text = list[position].detial
                Glide.with(context).load(list[position].cover).into(holder.imageview)
                holder.button1.text = "未选中"
                if(!sign){
                    holder.button1.visibility = View.GONE
                    holder.button.visibility = View.VISIBLE
                }
                holder.button.text = "继续观看"
                if(sign){
                    holder.button1.visibility = View.VISIBLE
                    holder.button.visibility = View.GONE
                }
                holder.itemView.setOnClickListener(View.OnClickListener {
                    if(sign){
                        flag1 = 1
                        if (position in positionList){
                            positionList.remove(position)
                            deleteList.remove(list[position])
                            holder.button1.text = "未选中"
                            Log.d("10086","取消一个")
                        }
                        else {
                            positionList.add(position)
                            deleteList.add(list[position])
                            holder.button1.text = "已选中"
                            Log.d("10086","选中一个")
                        }
                    }
                })

                if(deletesign == -1) {
                    deletesign == 0
                }
                holder.button.setOnClickListener(View.OnClickListener {
                    var data : Manhua = list[position]
                    var intent = Intent()
                    getChapterData(data.comicid)
                    intent.setClass(MyApplication.getContext(), ReadActivity::class.java)
                    intent.putExtra("chapterid",data.chapterid)
                    intent.putStringArrayListExtra("chapteridlist",chapterList)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    MyApplication.getContext().startActivity(intent)
                })
            }
        }
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
                    for(chapter1 in chapter.data.returnData.chapter_list){
                        chapterList.add(chapter1.chapter_id)
                    }
                }

            }
            override fun onFailure(call: Call<ComicChapter>, t: Throwable) {
                t.printStackTrace()
            }
        })

    }

}