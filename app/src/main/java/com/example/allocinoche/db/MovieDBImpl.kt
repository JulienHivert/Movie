package com.example.allocinoche.db

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.allocinoche.data.Movie
import com.example.allocinoche.db.dao.ResultDAO
import com.example.allocinoche.db.entities.MovieEntity
import com.example.allocinoche.db.mapper.toEntity
import com.example.allocinoche.db.mapper.toPojos
import io.reactivex.Completable
import io.reactivex.Maybe

/*
 * Dans cette classe on joue avec les POJO
 * Dans le DAO on joue uniquement avec les entities
 *  => du coup on utilise des mappers pour pouvoir dans un sens sauvegarer
 * les pojo vers les entities, et recupérer les entities pour
 * les transformer les pojos et utiliser les pojos dans l'app
 */

@Database(entities = [MovieEntity::class], version = 1 )
@TypeConverters(Converters::class)
abstract class MovieDBImpl: RoomDatabase(), MovieDB  {

    abstract val movieDB: ResultDAO

    override fun insertLikedMovie(movie: Movie): Completable {
        movieDB.insertMovie(movie.toEntity())
        return Completable.complete().doOnComplete { Log.i("Movie Database", "movie correctly saved") }
    }

    override fun getLikedMovies(): Maybe<List<Movie>> {
        return movieDB
            .getAllMoviesLiked()
            .map { it.toPojos() }
            .doOnSuccess {Log.i("get Movie: ", "$it")}
    }

    override fun deleteLikedMovie(movie: Movie): Completable {
        return movieDB.deleteMovie(movie.toEntity())
    }
}