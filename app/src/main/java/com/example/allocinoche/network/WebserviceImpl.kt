package com.example.allocinoche.network

import android.util.Log
import com.example.allocinoche.data.ServerResult
import com.example.allocinoche.data.TrailerResult
import com.facebook.stetho.Stetho
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class WebserviceImpl(baseUrl: String): Webservice {

    private interface EndPoint {
        @Headers("Content-Type: application/json")
        @GET("discover/movie")
        fun getMovies(@Query("api_key") token: String, @Query("page") page: Int): Single<ServerResult>

        @Headers("Content-Type: application/json")
        @GET("movie/top_rated")
        fun getPopularMovies(@Query("api_key") token: String): Single<ServerResult>

        @Headers("Content-Type: application/json")
        @GET("movie/upcoming")
        fun getUpcomingMovies(@Query("api_key") token: String): Single<ServerResult>

        @Headers("Content-Type: application/json")
        @GET("movie/{movie_id}/videos")
        fun getMovieTrailer(@Path("movie_id") movieID: Int, @Query("api_key") token: String): Single<TrailerResult>

    }

    private val retrofit: Retrofit

    init {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            val httpClient = OkHttpClient.Builder()
            httpClient.interceptors().add(logging)
            httpClient.connectTimeout(30, TimeUnit.SECONDS)
            httpClient.readTimeout(30, TimeUnit.SECONDS)
            httpClient.writeTimeout(30, TimeUnit.SECONDS)
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build()
        }

    private val endPoint = retrofit.create(EndPoint::class.java)

    override fun getMovies(token: String, page: Int): Single<ServerResult> {
        return endPoint
            .getMovies(token, page)
            .doOnError { error -> Log.e("Error: ", " $error")  }
            .doOnSuccess {success ->Log.i("Success: ", "$success") }
    }

    override fun getPopularMovies(token: String): Single<ServerResult> {
        return endPoint
            .getPopularMovies(token)
            .doOnError { error -> Log.e("Error: ", "$error" ) }
            .doOnSuccess { success -> Log.i("Success: ","$success") }
    }

    override fun getUpcomingMovies(token: String): Single<ServerResult> {
        return endPoint
            .getUpcomingMovies(token)
            .doOnError { error -> Log.e("Error: ", "$error" ) }
            .doOnSuccess { success -> Log.i("Success: ","$success") }
    }

    override fun getMovieTrailer(movieID: Int, token: String): Single<TrailerResult> {
        return endPoint.getMovieTrailer(movieID, token)
            .doOnError { error -> Log.e("Error: ", "$error") }
            .doOnSuccess { success -> Log.i("Trailer Success:", "${success.trailers}") }
    }
}