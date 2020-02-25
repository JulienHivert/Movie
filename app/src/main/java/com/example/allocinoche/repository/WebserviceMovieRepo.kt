package com.example.allocinoche.repository

import com.example.allocinoche.data.ServerResult
import com.example.allocinoche.data.TrailerResult
import io.reactivex.Single

interface WebserviceMovieRepo {

    fun getMovies(token: String, page: Int): Single<ServerResult>
    fun getPopularMovies(token: String): Single<ServerResult>
    fun getUpcomingMovies(token: String): Single<ServerResult>
    fun getMovieTrailer(movieID: Int?, token: String): Single<TrailerResult>
}