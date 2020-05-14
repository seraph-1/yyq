package com.example.u17lite

import com.example.u17lite.ui.ComicChapter
import com.example.u17lite.ui.ComicImage
import com.example.u17lite.ui.ComicItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GetRequest {
    //排行榜
    @GET("/v3/appV3_3/android/phone/list/getRankComicList")
    fun getData1(@Query("period") period : String, @Query("type") type : String, @Query("come_from") come_from : String, @Query("serialNumber") serialNumber : String, @Query("v") v : String, @Query("model") model : String, @Query("android_id") android_id : String, @Query("page") page : String) : Call<ComicItem>
    //搜索
    @GET("/v3/appV3_3/android/phone/search/searchResult")
    fun getData2(@Query("come_from") come_from : String, @Query("serialNumber") serialNumber : String, @Query("v") v : String, @Query("model") model : String, @Query("android_id") android_id : String,@Query("q") q : String) : Call<ComicItem>
    //漫画目录
    @GET("/v3/appV3_3/android/phone/comic/detail_static_new")
    fun getData3(@Query("come_from") come_from : String, @Query("serialNumber") serialNumber : String, @Query("v") v : String, @Query("model") model : String, @Query("android_id") android_id : String,@Query("comicid") comicid : String) : Call<ComicChapter>
    //漫画页面
    @GET("/v3/appV3_3/android/phone/comic/chapterNew")
    fun getData4(@Query("come_from") come_from : String, @Query("serialNumber") serialNumber : String, @Query("v") v : String, @Query("model") model : String, @Query("chapter_id") chapter_id : String, @Query("android_id") android_id : String) : Call<ComicImage>
}