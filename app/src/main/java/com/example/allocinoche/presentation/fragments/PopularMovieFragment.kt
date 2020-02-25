package com.example.allocinoche.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.allocinoche.R
import com.example.allocinoche.data.Movie
import com.example.allocinoche.observeSafe
import com.example.allocinoche.presentation.MovieAdapter
import com.example.allocinoche.presentation.activity.MainActivity
import com.example.allocinoche.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.popular_movie_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class PopularMovieFragment: Fragment() {

    private val movieViewModel: MovieViewModel by sharedViewModel()

    companion object {
        fun newInstance(): PopularMovieFragment {
            return PopularMovieFragment()
        }
        val TAG = PopularMovieFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.popular_movie_fragment, container, false)
        initObserver()
        movieViewModel.getPopularMovies()
        return view
    }

    private fun initObserver(){
        popularMovieObserver()
    }

    private fun popularMovieObserver() {
        movieViewModel.popularMovies.observeSafe(this) {
            Log.i(TAG, "${it.movies}")
            displayPopularMovies(it.movies)
        }
    }

    private fun displayPopularMovies(popularMovie: List<Movie>) {
        recycler_view_popular_movie.layoutManager = LinearLayoutManager(activity)
        recycler_view_popular_movie.adapter = MovieAdapter(popularMovie) {
            onMovieClicked(it)
        }
        recycler_view_popular_movie?.adapter?.notifyDataSetChanged()
    }

    private fun onMovieClicked(movie: Movie) {
        (activity as MainActivity).navigator.showPopularMovieFragment()
    }
}
