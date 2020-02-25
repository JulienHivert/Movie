package com.example.allocinoche.db

import com.example.allocinoche.data.Movie
import com.example.allocinoche.db.entities.MovieEntity
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface MovieDB {

    fun insertLikedMovie(movieMovieEntity: Movie): Completable
    fun getLikedMovies(): Maybe<List<Movie>>
    fun deleteLikedMovie(movieMovieEntity: Movie): Completable
}