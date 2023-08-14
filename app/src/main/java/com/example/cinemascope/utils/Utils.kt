package com.example.cinemascope.utils

fun getYoutubeThumbnailUrlFromVideoUrl(videoKey: String): String {
    return "https://img.youtube.com/vi/$videoKey/0.jpg"
}

fun getYoutubeVideoUrlFromVideoKey(videoKey: String): String {
    return "https://www.youtube.com/watch?v=$videoKey"
}
