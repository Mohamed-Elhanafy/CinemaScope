package com.example.cinemascope.data


import com.google.gson.annotations.SerializedName

data class ShowResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Show>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)