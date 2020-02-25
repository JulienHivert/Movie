package com.example.allocinoche.repository

import com.example.allocinoche.data.Movie
import io.reactivex.Completable
import io.reactivex.Maybe

interface DatabaseMovieRepo {
    fun loadMovieFromDB(): Maybe<List<Movie>>
    fun addMovieInsideDB(movie: Movie): Completable
    fun deleteMovieInsideDB(movie: Movie): Completable
}
