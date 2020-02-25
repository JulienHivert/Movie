package com.example.allocinoche.repository

import com.example.allocinoche.data.ServerResult
import com.example.allocinoche.data.TrailerResult
import com.example.allocinoche.network.Webservice
import io.reactivex.Single

class WebserviceMovieRepoImpl(private val webservice: Webservice): WebserviceMovieRepo  {

    override fun getMovies(token: String, page: Int): Single<ServerResult> {
        return Single.defer {
            webservice.getMovies(token, page)
        }
    }
    override fun getPopularMovies(token: String): Single<ServerResult> {
        return Single.defer {
            webservice.getPopularMovies(token)
        }
    }

    override fun getUpcomingMovies(token: String): Single<ServerResult> {
        return Single.defer {
            webservice.getUpcomingMovies(token)
        }
    }

    override fun getMovieTrailer(movieID: Int?, token: String): Single<TrailerResult> {
        return Single.defer{
            movieID?.let { webservice.getMovieTrailer(it, token) }
        }
    }
}
