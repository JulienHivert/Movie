package com.example.allocinoche.presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.allocinoche.R
import com.example.allocinoche.data.Movie
import com.example.allocinoche.data.Trailer
import com.example.allocinoche.observeSafe
import com.example.allocinoche.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.detail_movie_fragment.*
import kotlinx.android.synthetic.main.trailer.view.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class DetailsMovieFragment: Fragment() {

    private val movieViewModel: MovieViewModel by sharedViewModel()

    companion object {
        fun newInstance(movie: Movie): DetailsMovieFragment {
            val fragment = DetailsMovieFragment()
            val args = Bundle()
            args.putParcelable("movie", movie)
            fragment.arguments = args
            return fragment
        }
        val TAG = DetailsMovieFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.detail_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val b = arguments
        val movie: Movie? = b?.getParcelable("movie")
        initView(movie)
        saveMovie(movie)
    }

    private fun initView(movie: Movie?) {
        movieDetailsTitle.text = movie?.originalTitle
        movieDetailsReleaseDate.text = movie?.releaseDate
        movieDetailsOverview.text = movie?.overview
        summaryLabel.text = movie?.overview
        if (movie != null) {
            movieDetailsRating.visibility = View.VISIBLE
            movieDetailsRating.rating = (movie.voteAverage / 2).toFloat()
        }
        context?.let { Glide.with(it).load("http://image.tmdb.org/t/p/w780"+movie?.backdropPath).into(movieDetailsBackdrop) }
        movieViewModel.getMovieTrailer(movie?.id)
        observeMovieTrailer()
    }

    private fun observeMovieTrailer() {
        movieViewModel.movieTrailers.observeSafe(this){
            displayMovieTrailer(it.trailers)
        }
    }
    private fun displayMovieTrailer(trailers: List<Trailer>) {
        Log.i(TAG, "$trailers")
        for(trailer in trailers) {
            val movieTrailerView: View =
                LayoutInflater.from(context).inflate(R.layout.trailer, movieReviews, false)
            val thumbnail = movieTrailerView.thumbnail
            thumbnail.setOnClickListener {
                showTrailer(String.format("http://www.youtube.com/watch?v=%s", trailer.key))
            }
        }
    }

    private fun showTrailer(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun saveMovie(movie: Movie?) {
        likedMovie.setOnClickListener {
            Log.i(TAG, "clicked")
            movie?.let { movie -> movieViewModel.addMovieToStore(movie) }
        }
    }
}