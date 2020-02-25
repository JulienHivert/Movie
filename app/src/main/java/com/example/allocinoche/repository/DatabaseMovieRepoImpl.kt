package com.example.allocinoche.repository

import com.example.allocinoche.data.Movie
import com.example.allocinoche.db.MovieDB
import io.reactivex.Completable
import io.reactivex.Maybe

class DatabaseMovieRepoImpl(val database: MovieDB): DatabaseMovieRepo {

    override fun loadMovieFromDB(): Maybe<List<Movie>> {
        return database.getLikedMovies()
    }

    override fun addMovieInsideDB(movie: Movie): Completable {
        return Completable.defer{
            database.insertLikedMovie(movie)
        }
    }

    override fun deleteMovieInsideDB(movie: Movie): Completable {
        return Completable.defer{
            database.deleteLikedMovie(movie)
        }
    }
}
