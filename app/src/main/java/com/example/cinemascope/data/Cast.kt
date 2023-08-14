package com.example.cinemascope.data


import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("cast")
    val cast: List<CastX>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)