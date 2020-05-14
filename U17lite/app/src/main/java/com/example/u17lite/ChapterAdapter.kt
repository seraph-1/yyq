package com.example.u17lite

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ManhuaDaoOpe
import com.bumptech.glide.Glide
import com.example.u17lite.ui.Chapter
import com.example.u17lite.ui.ComicChapter

class ChapterAdapter(private val context : Context,  private val data : Manhua) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var list : ArrayList<Chapter> = ArrayList()
    var chapterIdList : ArrayList<String> = ArrayList()


    fun addData(chapter : ComicChapter){
        list.addAll(chapter.data.returnData.chapter_list)
        for(chapter1 in list){
            chapterIdList.add(chapter1.chapter_id)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = View.inflate(context,R.layout.chapter_item,null)
        var holder = ViewHolder(view)
        holder.itemView.setOnClickListener(View.OnClickListener {
            var position = holder.adapterPosition
            var chapter = list[position]
            var intent = Intent()
            data.chapterid = chapter.chapter_id
            ManhuaDaoOpe.getInstance().insertHistoryData(context,data)
            intent.setClass(MyApplication.getContext(),ReadActivity::class.java)
            intent.putExtra("chapterid",chapter.chapter_id)
            intent.putStringArrayListExtra("chapteridlist",chapterIdList)
            intent.flags = FLAG_ACTIVITY_NEW_TASK
            MyApplication.getContext().startActivity(intent)
        })
        return holder
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder)
        {
            Glide.with(MyApplication.getContext()).load(list[position].smallPlaceCover).error(R.mipmap.ic_launcher_round).into(holder.img)
            holder.tv.text = list[position].name
        }
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var img : ImageView = view.findViewById(R.id.chapter_img)
        var tv : TextView = view.findViewById(R.id.chapter_name)
    }

}