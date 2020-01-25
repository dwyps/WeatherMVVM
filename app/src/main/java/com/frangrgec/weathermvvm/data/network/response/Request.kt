package com.frangrgec.weathermvvm.data.network.response


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Request(
    val type: String,
    val query: String,
    val language: String,
    val unit: String
)