package com

import android.content.Context
import com.example.u17lite.DbManager
import com.example.u17lite.Manhua
import com.greendao.ManhuaDao

class ManhuaDaoOpe private constructor() {
    private object mHolder {
        val instance = ManhuaDaoOpe()
    }

    companion object {
        fun getInstance(): ManhuaDaoOpe {
            return mHolder.instance
        }
    }


    fun insertCollectData(context: Context?, manhua: Manhua) {
        manhua.collect = true
        if(queryHistoryForName(context,manhua.name)?.size != 0){
            manhua.history = true
            manhua.chapterid = queryHistoryForName(context,manhua.name)?.get(0)?.chapterid
            updateData(context,manhua)
        }
        else if (queryCollectForName(context,manhua.name)?.size != 0)updateData(context,manhua)
        else DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.insert(manhua)
    }


    fun insertHistoryData(context: Context?, manhua: Manhua) {
        manhua.history = true
        if(queryCollectForName(context,manhua.name)?.size != 0){
            manhua.collect = true
            if (queryHistoryForName(context,manhua.name)?.size != 0)
            manhua.chapterid = queryHistoryForName(context,manhua.name)?.get(0)?.chapterid
            updateData(context,manhua)
        }
        else if(queryHistoryForName(context,manhua.name)?.size != 0)updateData(context,manhua)
        else DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.insert(manhua)
    }


    fun insertData(context: Context?, list: List<Manhua>?) {
        if (null == list || list.size <= 0) {
            return
        }
        DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.insertInTx(list)
    }



    fun deleteDataForName(context: Context?, name:String) {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.queryBuilder()
        var manhua : Manhua? = builder?.where(ManhuaDao.Properties.Name.eq(name))?.list()?.get(0)
        DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.delete(manhua)
    }

    fun deleteCollectData(context: Context?,manhua: Manhua) {
        manhua.collect = false
        if (queryHistoryForName(context,manhua.name)?.size != 0) {
            manhua.history = true
            manhua.chapterid = queryHistoryForName(context,manhua.name)?.get(0)?.chapterid
        }
        updateData(context,manhua)
    }

    fun deleteHistoryData(context: Context?,manhua: Manhua) {
        manhua.history = false
        if (queryCollectForName(context,manhua.name)?.size != 0){
            manhua.collect = true
        }
        updateData(context,manhua)
    }


    fun updateData(context: Context?, manhua: Manhua) {
        DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.update(manhua)
        if(!manhua.history && !manhua.collect)
            DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.delete(manhua)
    }



    fun queryAllCollect(context: Context?): MutableList<Manhua>? {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.queryBuilder()
        return builder?.where(ManhuaDao.Properties.Collect.eq(true))?.list()
    }

    fun queryAllHistory(context: Context?): MutableList<Manhua>? {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.queryBuilder()
        return builder?.where(ManhuaDao.Properties.History.eq(true))?.list()
    }


    fun queryHistoryForName(context: Context?, name:String): MutableList<Manhua>? {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.queryBuilder()
        return builder?.where(ManhuaDao.Properties.Name.eq(name))?.where(ManhuaDao.Properties.History.eq(true))?.list()
    }

    fun queryCollectForName(context: Context?, name:String): MutableList<Manhua>? {
        val builder = DbManager.getInstance(context!!)?.getDaoSession(context)?.manhuaDao?.queryBuilder()
        return builder?.where(ManhuaDao.Properties.Name.eq(name))?.where(ManhuaDao.Properties.Collect.eq(true))?.list()
    }

}