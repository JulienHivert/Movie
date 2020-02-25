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
import kotlinx.android.synthetic.main.movie_stored.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class MovieStoredFragment: Fragment() {

    private val movieViewModel: MovieViewModel by sharedViewModel()

    companion object {
        fun newInstance(): MovieStoredFragment{
            return MovieStoredFragment()
        }
         val TAG = MovieStoredFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movie_stored, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        initRecycler()
        movieViewModel.loadMovieStored()
    }

    private fun initObserver() {
        movieViewModel.movieStored.observeSafe(this) {
            if(it.isNotEmpty()) {
                Log.i(TAG, "$it")
                stored_movie.visibility = View.GONE
                recycler_view_storedMovie.visibility = View.VISIBLE
                displayMovieStored(it)
            } else {
                stored_movie.text = "No film added yet"
            }
        }
    }

    private fun initRecycler() {
        val adapter = LinearLayoutManager(activity)
        recycler_view_storedMovie.layoutManager = adapter
        recycler_view_storedMovie.setHasFixedSize(true)
    }

    private fun displayMovieStored(movieList: List<Movie>) {
        recycler_view_storedMovie.adapter = MovieAdapter(movieList) {

        }
    }
    private fun onMovieClicked(movie: Movie) {
        (activity as MainActivity).showRoomDetailActionBar(movie)
        (activity as MainActivity).navigator.displayMovieDetails(movie)
    }
}
