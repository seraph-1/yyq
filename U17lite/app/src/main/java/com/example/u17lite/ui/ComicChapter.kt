package com.example.u17lite.ui

data class ComicChapter(
    val code: Int,
    val `data`: ComicChapterData
)

data class ComicChapterData(
    val message: String,
    val returnData: ComicChapterReturnData,
    val stateCode: Int
)

data class ComicChapterReturnData(
    val chapter_list: List<Chapter>,
    val commentCount: Int
)

data class Chapter(
    val chapterIndex: Int,
    val chapter_id: String,
    val has_locked_image: Boolean,
    val image_total: String,
    val index: String,
    val is_new: Int,
    val name: String,
    val pass_time: Int,
    val publish_time: Int,
    val release_time: String,
    val size: String,
    val smallPlaceCover: String,
    val type: Int,
    val vip_images: String,
    val zip_high_webp: String
)