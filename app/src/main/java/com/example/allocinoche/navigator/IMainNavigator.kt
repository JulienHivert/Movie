package com.example.allocinoche.navigator

import com.example.allocinoche.data.Movie

interface IMainNavigator {

    fun showDiscoveredMovieFragment()
    fun showPopularMovieFragment()
    fun showUpcomingMovieFragment()
    fun displayMovieDetails(movie: Movie)
    fun showMovieLikedByUser()
    fun onBackPressed()
}