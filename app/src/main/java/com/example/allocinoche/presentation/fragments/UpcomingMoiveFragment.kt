package com.example.allocinoche.presentation.fragments

import android.os.Bundle
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
import kotlinx.android.synthetic.main.upcoming_movie_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class UpcomingMoiveFragment: Fragment() {

    private val movieViewModel: MovieViewModel by sharedViewModel()


    companion object {
        fun newInstance(): UpcomingMoiveFragment {
            return UpcomingMoiveFragment()
        }
        val TAG = UpcomingMoiveFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.upcoming_movie_fragment, container, false)
        initObserver()
        movieViewModel.getUpcomingMovies()
        return view
    }

    private fun initObserver(){
        upcomingMovieObsderver()
    }

    private fun upcomingMovieObsderver() {
        movieViewModel.upcomingMovies.observeSafe(this){
            displayUpcomingMovie(it.movies)
        }
    }

    private fun displayUpcomingMovie(upcomingMovie: List<Movie>) {
        recycler_view_upcoming_movie.layoutManager = LinearLayoutManager(activity)
        recycler_view_upcoming_movie.adapter = MovieAdapter(upcomingMovie) {
            showMovie(it)
        }
    }

    private fun showMovie(movie: Movie) {
        (activity as MainActivity).navigator.showPopularMovieFragment()
    }
}
