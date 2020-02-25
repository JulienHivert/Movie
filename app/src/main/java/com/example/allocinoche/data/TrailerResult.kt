package com.example.allocinoche.data

import com.google.gson.annotations.SerializedName

class TrailerResult(
    @SerializedName("results") var trailers: List<Trailer>)
