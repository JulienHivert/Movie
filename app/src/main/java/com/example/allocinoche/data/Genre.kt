package com.example.allocinoche.data

import com.google.gson.annotations.SerializedName

class Genre(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String)