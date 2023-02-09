package com.example.retrofitexample


import com.google.gson.annotations.SerializedName

data class DogsItems(
    @SerializedName("message")
    val images: List<String>,
    @SerializedName("status")
    val status: String
)