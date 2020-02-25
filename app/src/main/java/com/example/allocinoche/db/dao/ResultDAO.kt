package com.example.allocinoche.db.dao

import androidx.room.*
import com.example.allocinoche.db.entities.MovieEntity
import io.reactivex.Completable
import io.reactivex.Maybe


@Dao
interface ResultDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity): Completable

    @Query("SELECT * FROM movieDB")
    fun getAllMoviesLiked(): Maybe<List<MovieEntity>>

    @Delete
    fun deleteMovie(movie: MovieEntity): Completable
}