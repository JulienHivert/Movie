package com.example.allocinoche.network

import com.example.allocinoche.data.ServerResult
import com.example.allocinoche.data.TrailerResult
import io.reactivex.Single
import retrofit2.http.Path
import retrofit2.http.Query

interface Webservice {

    fun getMovies(@Query("api_key")token: String, @Query("page") page: Int): Single<ServerResult>
    fun getPopularMovies(@Query("api_key") token: String): Single<ServerResult>
    fun getUpcomingMovies(@Query("api_key") token: String): Single<ServerResult>
    fun getMovieTrailer(@Path("movie_id") movieID: Int, @Query("api_key") token: String): Single<TrailerResult>
}