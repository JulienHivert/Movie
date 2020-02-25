package com.example.allocinoche.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.allocinoche.SingleLiveEvent
import com.example.allocinoche.data.Movie
import com.example.allocinoche.data.ServerResult
import com.example.allocinoche.data.Status
import com.example.allocinoche.data.TrailerResult
import com.example.allocinoche.presentation.component.InternetCheck
import com.example.allocinoche.repository.DatabaseMovieRepo
import com.example.allocinoche.repository.WebserviceMovieRepo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class MovieViewModel(private val webserviceMovieRepo: WebserviceMovieRepo, private val databaseMovieRepo: DatabaseMovieRepo): ViewModel() {

    val discoveredMovies = MutableLiveData<ServerResult>()
    val upcomingMovies = MutableLiveData<ServerResult>()
    val popularMovies = MutableLiveData<ServerResult>()
    val movieTrailers = MutableLiveData<TrailerResult>()
    val currentMovieLiveData = MutableLiveData<Movie>()
    val movieStored = MutableLiveData<List<Movie>>()
    val movieAddLiveData = MutableLiveData<Movie>()
    val movieAdded = SingleLiveEvent<Unit>()
    val movieDeleted = SingleLiveEvent<Unit>()

    val status = MutableLiveData<Status>()

    val movieToSync: Movie? = null

    private var popularMoviesDisposable = CompositeDisposable()
    private var moviesDisposable = CompositeDisposable()
    private var upComingMoviesDisposable = CompositeDisposable()
    private var movieTrailerDisposable = CompositeDisposable()
    private var movieStoredDisposable = CompositeDisposable()
    private var movieAddedDisposable = CompositeDisposable()
    private var movieDeletedDisposable = CompositeDisposable()

    var currentMovie: Movie? = null
    set(newValue) {
        Log.d(TAG, "set current diffuser value : old value $field new value $newValue")
        if (newValue == field){
            return
        }
        when {
            newValue == null -> {
                Log.d(TAG, "current movie == null")
            }
            field != null -> {
                field = newValue
                currentMovieLiveData.postValue(field)
            }
        }
    }

    fun getDiscoveredMovie(page: Int) {
        val d = webserviceMovieRepo.getMovies("af742b6fa65d39b70cbe8077cf07c8b3", page)
            .subscribeOn(Schedulers.io())
            .subscribeBy(onError = {
                status.postValue(Status.ERROR)
            },
                onSuccess = { movie ->
                    discoveredMovies.postValue(movie)
                    status.postValue(Status.SUCCESS)
                })

        moviesDisposable.add(d)
    }

    fun getUpcomingMovies() {
        val d = webserviceMovieRepo.getUpcomingMovies("af742b6fa65d39b70cbe8077cf07c8b3")
            .subscribeOn(Schedulers.io())
            .subscribeBy(onError = {
                status.postValue(Status.ERROR)
            },
                onSuccess = { upcomingMovie ->
                    upcomingMovies.postValue(upcomingMovie)
                    status.postValue(Status.SUCCESS)
                }
            )
        popularMoviesDisposable.add(d)
    }

    fun getPopularMovies() {
        val d = webserviceMovieRepo.getPopularMovies("af742b6fa65d39b70cbe8077cf07c8b3")
            .subscribeOn(Schedulers.io())
            .subscribeBy(onError = {
                status.postValue(Status.ERROR)
            },
                onSuccess = { popularMovie ->
                    popularMovies.postValue(popularMovie)
                    status.postValue(Status.SUCCESS)
                }
            )
        upComingMoviesDisposable.add(d)
    }

    fun checkNetwork(context: Context) {
        val internetAvailable = InternetCheck.isConnected(context)
        if (internetAvailable) {
            status.postValue(Status.CONNECTED)
        } else {
            status.postValue(Status.NOT_CONNECTED)
        }
    }

    fun getMovieTrailer(movieID: Int?) {
        val d = webserviceMovieRepo.getMovieTrailer(movieID, "af742b6fa65d39b70cbe8077cf07c8b3")
            .subscribeOn(Schedulers.io())
            .subscribeBy(onError = {
                status.postValue(Status.ERROR)
            }, onSuccess = { trailer ->
                movieTrailers.postValue(trailer)
            })

        movieTrailerDisposable.add(d)
    }

    fun loadMovieStored() {
        val d = databaseMovieRepo.loadMovieFromDB()
            .subscribeOn(Schedulers.io())
            .subscribeBy( onError = {
                status.postValue(Status.ERROR)
            }, onSuccess = {
                status.postValue(Status.CONNECTED)
                movieStored.postValue(it)
            })
        movieStoredDisposable.add(d)
    }

    fun addMovieToStore(movie: Movie) {
        movie.let {
            val d = databaseMovieRepo.addMovieInsideDB(it)
                .subscribeOn(Schedulers.io())
                .subscribeBy(onError = {
                    status.postValue(Status.ERROR)
                },
                    onComplete = {
                        Log.e(TAG, "Movie $it stored")
                        status.postValue(Status.SUCCESS)
                    })
            movieAddedDisposable.add(d)
        }
    }

    fun deleteMovieToStore(movie: Movie) {
            val d = databaseMovieRepo.deleteMovieInsideDB(movie)
                .subscribeOn(Schedulers.io())
                .subscribeBy(onError = {
                    status.postValue(Status.ERROR)
                }, onComplete = {
                    movieDeleted.postValue(Unit)
                })
            movieDeletedDisposable.add(d)
    }

    override fun onCleared() {
        moviesDisposable.clear()
        popularMoviesDisposable.clear()
        upComingMoviesDisposable.clear()
        movieTrailerDisposable.clear()
        movieStoredDisposable.clear()
        movieAddedDisposable.clear()
        movieDeletedDisposable.clear()
        super.onCleared()
    }

    companion object {
        private var TAG = MovieViewModel::class.java.simpleName
    }
}