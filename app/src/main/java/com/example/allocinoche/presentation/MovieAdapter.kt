package com.example.allocinoche.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.allocinoche.R
import com.example.allocinoche.data.Movie
import kotlinx.android.synthetic.main.movie.view.*

class MovieAdapter(var list: List<Movie>, private val clickListener: (Movie) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = list[position]
        holder.bind(movie, clickListener)
        //holder.bindMovieGenre(movie)
    }

    inner class MovieViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            itemView.item_movie_release_date.text = movie.releaseDate
            itemView.item_movie_title.text = movie.title
            Glide.with(itemView)
                .load("http://image.tmdb.org/t/p/w500${movie.posterPath}")
                .apply(RequestOptions.placeholderOf(R.color.colorPrimary))
                .into(itemView.item_movie_poster)
            itemView.setOnClickListener { clickListener(movie) }
        }

        /*
        fun bindMovieGenre(movie: MovieEntity) {
            for (genre in movie.genreIds) {
                Log.i("tmp", "$genre")
            }
        } */
    }
}