package com.example.allocinoche.data


import com.google.gson.annotations.SerializedName

data class ServerResult(
    var page: Int,
    @SerializedName("total_results")
    var totalResults: Int,
    @SerializedName("total_pages")
    var totalPages: Int,
    @SerializedName("results")
    var movies: MutableList<Movie>
)