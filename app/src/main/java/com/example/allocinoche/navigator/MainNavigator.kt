package com.example.allocinoche.navigator

import androidx.appcompat.app.AppCompatActivity
import com.example.allocinoche.R
import com.example.allocinoche.data.Movie
import com.example.allocinoche.presentation.fragments.*

class MainNavigator(private val activity: AppCompatActivity): IMainNavigator {

    private val rootContainer = R.id.my_nav_host_fragment
    private val fragmentManager = activity.supportFragmentManager

    override fun showDiscoveredMovieFragment() {
        val fragment = DiscoverMoviesFragment.newInstance()
        fragmentManager
            .beginTransaction()
            .replace(rootContainer, fragment)
            .commit()
    }

    override fun showPopularMovieFragment() {
        val fragment = PopularMovieFragment.newInstance()
        fragmentManager
            .beginTransaction()
            .replace(rootContainer, fragment)
            .commit()
    }

    override fun showUpcomingMovieFragment() {
        val fragment = UpcomingMoiveFragment.newInstance()
        fragmentManager
            .beginTransaction()
            .replace(rootContainer, fragment)
            .commit()
    }

    override fun displayMovieDetails(movie: Movie) {
        val fragment = DetailsMovieFragment.newInstance(movie)
        fragmentManager
            .beginTransaction()
            .addToBackStack(DetailsMovieFragment::class.java.name)
            .replace(rootContainer, fragment)
            .commit()
    }

    override fun showMovieLikedByUser() {
        val fragment = MovieStoredFragment.newInstance()
        fragmentManager
            .beginTransaction()
            .replace(rootContainer, fragment)
            .commit()
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            activity.finish()
        }
    }
}