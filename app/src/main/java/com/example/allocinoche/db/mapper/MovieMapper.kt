package com.example.allocinoche.db.mapper

import com.example.allocinoche.data.Movie
import com.example.allocinoche.db.entities.MovieEntity


// Mapper qui permet de recupérer les données confomrent au POJO
fun MovieEntity.toPojo(): Movie {
    return Movie(uId,title,video, originalTitle, vote, popularity, originalLanguage, voteCount,
        posterPath, genre, backDropPath, adult, overview, releaseDate)
}

fun List<MovieEntity>.toPojos(): List<Movie> {
    return map { it.toPojo() }
}

// Mapper qui permet d'enregistrer les données conforment à ce que la BDD attend
fun Movie.toEntity(): MovieEntity {
    return MovieEntity(id, originalTitle, posterPath, originalTitle, video, voteAverage, popularity,
        originalLanguage, voteCount, posterPath, genreIds, backdropPath, adult, overview, releaseDate, liked = false)
}

fun List<Movie>.toEntities(): List<MovieEntity> {
    return map { it.toEntity() }
}
// /data/app/com.sleepmonitor.aio-XpTegeBFawzKgISn3olUxw