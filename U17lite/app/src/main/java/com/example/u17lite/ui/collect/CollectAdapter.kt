package com.example.u17lite.ui.collect

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ManhuaDaoOpe
import com.bumptech.glide.Glide
import com.example.u17lite.DetialActivity
import com.example.u17lite.Manhua
import com.example.u17lite.MyApplication
import com.example.u17lite.R

class CollectAdapter(private val context : Context, private val manhuaList : MutableList<Manhua>) : BaseAdapter() {

    var deleteList1 : ArrayList<Manhua> = ArrayList()
    var positionList1 : ArrayList<Int> = ArrayList()
    var sign1 = false

    fun clear() {
        deleteList1.clear()
        positionList1.clear()
    }

    fun getsign1() : Boolean{
        return sign1
    }

    fun setsign1(sign1 : Boolean){
        this.sign1 = sign1
    }

    fun removeData(i : Manhua){
        manhuaList.remove(i)
        notifyDataSetChanged()
    }

    fun yessignal() {
        for (i in deleteList1){
            ManhuaDaoOpe.getInstance()
                .deleteCollectData(MyApplication.getContext(), i)
            removeData(i)
        }
        positionList1.clear()
        deleteList1.clear()
    }

    fun nosignal() {
            deleteList1.clear()
            positionList1.clear()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        var holder : ViewHolder
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.collect_item,null)
            holder = ViewHolder()
            var data : Manhua = manhuaList[position]
            holder.imgview = view.findViewById(R.id.pic)
            holder.textview = view.findViewById(R.id.comicname)
            holder.button = view.findViewById(R.id.choose)
            Glide.with(MyApplication.getContext()).load(data.cover).override(1000,400).into(holder.imgview)
            holder.textview.text = data.name
            holder.button.visibility = View.GONE
            view.setOnClickListener(View.OnClickListener {
                if (!sign1)
                {
                    var intent = Intent()
                    intent.setClass(MyApplication.getContext(), DetialActivity::class.java)
                    intent.putExtra("name", data.name)
                    intent.putExtra("cover", data.cover)
                    intent.putExtra("tag", data.tag)
                    intent.putExtra("detial", data.detial)
                    intent.putExtra("id", data.comicid)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    MyApplication.getContext().startActivity(intent)
                }
                else {
                    Log.d("10086","edit mode")
                    if (position in positionList1){
                        positionList1.remove(position)
                        deleteList1.remove(data)
                        holder.button.visibility = View.GONE
                        Log.d("10086","取消了")
                    }
                    else {
                        positionList1.add(position)
                        deleteList1.add(data)
                        holder.button.setBackgroundResource(R.drawable.edge5)
                        holder.button.visibility = View.VISIBLE
                        Log.d("10086","选中了")
                    }
                }
            })
        }
        else return convertView
        return view
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return manhuaList.size
    }

    inner class ViewHolder{
         lateinit var imgview : ImageView
         lateinit var textview : TextView
         lateinit var button : Button
    }
}