package com.example.u17lite

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ManhuaDaoOpe
import com.bumptech.glide.Glide
import com.example.u17lite.ui.Comic
import com.example.u17lite.ui.ComicItem

class ComicAdapter(context1 : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var context : Context = context1
    var list : ArrayList<Manhua> = ArrayList()
    var isHistory : Boolean = false
    var isCollect : Boolean = false
    fun addData(comic : ComicItem){
        for(mic in comic.data.returnData.comics){
            isCollect = ManhuaDaoOpe.getInstance().queryCollectForName(MyApplication.getContext(),mic.name!!)?.size != 0
            isHistory = ManhuaDaoOpe.getInstance().queryHistoryForName(MyApplication.getContext(),mic.name!!)?.size != 0
            list.add(Manhua(mic.name,mic.cover,mic.tags[0],mic.description,mic.comicId,"",isCollect,isHistory))
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = View.inflate(context,R.layout.comic_item,null)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ViewHolder ->{
                holder.textView_title.text = list[position].name
                holder.textview_tag.text = list[position].tag
                holder.textview_detial.text = list[position].detial
                Glide.with(context).load(list[position].cover).into(holder.imageview)
                holder.button1.visibility = View.GONE
                if(ManhuaDaoOpe.getInstance().queryCollectForName(MyApplication.getContext(),list[position].name)?.size != 0){
                    holder.button.text = "已收藏"
                }
                else{
                    holder.button.text = "收藏"
                }
                holder.button.setOnClickListener {
                    if (ManhuaDaoOpe.getInstance().queryCollectForName(MyApplication.getContext(), list[position].name)?.size != 0) {
                            holder.button.text = "收藏"
                            ManhuaDaoOpe.getInstance().deleteCollectData(MyApplication.getContext(),list[position])
                            Toast.makeText(MyApplication.getContext(),"已取消收藏",Toast.LENGTH_SHORT).show()
                        }
                    else{
                        holder.button.text = "已收藏"
                        ManhuaDaoOpe.getInstance().insertCollectData(MyApplication.getContext(),list[position])
                        Toast.makeText(MyApplication.getContext(),"收藏成功",Toast.LENGTH_SHORT).show()
                    }
                }
                holder.itemView.setOnClickListener(View.OnClickListener {
                    var position = holder.adapterPosition
                    var data = list[position]
                    var intent = Intent()
                    intent.setClass(MyApplication.getContext(),DetialActivity::class.java)
                    intent.putExtra("name",data.name)
                    intent.putExtra("cover",data.cover)
                    intent.putExtra("tag",data.tag)
                    intent.putExtra("detial",data.detial)
                    intent.putExtra("id",data.comicid)
                    intent.flags = FLAG_ACTIVITY_NEW_TASK
                    MyApplication.getContext().startActivity(intent)
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var imageview : ImageView = view.findViewById(R.id.comic_pic)
        var textView_title : TextView = view.findViewById(R.id.comic_title)
        var textview_tag : TextView = view.findViewById(R.id.comic_tag)
        var textview_detial : TextView = view.findViewById(R.id.comic_detial)
        var button : Button = view.findViewById(R.id.comic_collect)
        var button1 : Button = view.findViewById(R.id.comic_choose)
    }

}