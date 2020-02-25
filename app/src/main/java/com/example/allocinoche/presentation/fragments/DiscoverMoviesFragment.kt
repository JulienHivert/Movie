package com.example.allocinoche.presentation.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.allocinoche.R
import com.example.allocinoche.data.Genre
import com.example.allocinoche.data.Movie
import com.example.allocinoche.observeSafe
import com.example.allocinoche.presentation.MovieAdapter
import com.example.allocinoche.presentation.activity.MainActivity
import com.example.allocinoche.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.discover_movie_fragment.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import kotlin.properties.Delegates


class DiscoverMoviesFragment: Fragment() {

    private val movieViewModel: MovieViewModel by sharedViewModel()
    private var page = 1
    private var currentPage by Delegates.notNull<Int>()

    companion object {
        fun newInstance(): DiscoverMoviesFragment {
            return DiscoverMoviesFragment()
        }
        val TAG = DiscoverMoviesFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.discover_movie_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieViewModel.getDiscoveredMovie(page)
        initRecycler()
        movieObserver()
    }

    private fun initRecycler() {
        val adapter = LinearLayoutManager(activity)
        discover_movie_recycler.layoutManager = adapter
        discover_movie_recycler.setHasFixedSize(true)
        discover_movie_recycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalCount = adapter.itemCount
                val visibleItemCount = adapter.childCount
                val firstItemPosition = adapter.findFirstVisibleItemPosition()
                if (firstItemPosition + visibleItemCount >= totalCount) {
                    Log.i("tmp_tmp", "$firstItemPosition : $visibleItemCount : $totalCount : $page")
                    currentPage = page + 1
                    movieViewModel.getDiscoveredMovie(currentPage)
                    page = currentPage
                }
            }
        })
    }

    private fun displayMovie(movie: List<Movie>) {
        discover_movie_recycler?.adapter = MovieAdapter(movie) {
            onMovieClicked(it)
        }
        discover_movie_recycler?.adapter?.notifyDataSetChanged()
    }

    private fun movieObserver() {
        activity?.let {
            movieViewModel.discoveredMovies.observeSafe(it) { movie ->
                if (movie != null) {
                    Log.i(TAG, "${movie.movies}")
                    displayMovie(movie.movies)
                } else {
                    Log.e("failed", "failed")
                }
            }
        }
    }

    private fun onMovieClicked(movie: Movie) {
        (activity as MainActivity).showRoomDetailActionBar(movie)
        (activity as MainActivity).navigator.displayMovieDetails(movie)
    }

}