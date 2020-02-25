package com.example.allocinoche.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.allocinoche.R
import com.example.allocinoche.data.Movie
import com.example.allocinoche.data.Status
import com.example.allocinoche.navigator.MainNavigator
import com.example.allocinoche.observeSafe
import com.example.allocinoche.presentation.component.ISnackbar
import com.example.allocinoche.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    val navigator: MainNavigator by currentScope.inject{ parametersOf(this) }
    private val movieViewModel : MovieViewModel by viewModel()
    private val snackbarComponent: ISnackbar by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigator.showDiscoveredMovieFragment()
        configureBottomView()
        statusObserver()
        showMainActivityActionBar()
        //movieViewModel.checkNetwork(this)
    }

    private fun configureBottomView() {
        navigation.setOnNavigationItemSelectedListener { item -> configureNavigationBottomBar(item.itemId) }
    }

    private fun configureNavigationBottomBar(itemID: Int) : Boolean {
        when(itemID) {
            R.id.action_discover -> {
                navigator.showDiscoveredMovieFragment()
                showMainActivityActionBar()
            }
            R.id.action_popular -> {
                navigator.showPopularMovieFragment()
                showMainActivityActionBar()
            }
            R.id.action_upcoming -> {
                navigator.showUpcomingMovieFragment()
                showMainActivityActionBar()
            }
            R.id.action_myMovie -> {
                navigator.showMovieLikedByUser()
                showMainActivityActionBar()
            }
        }
        return true
    }
    private fun statusObserver() {
        movieViewModel.status.observeSafe(this) { status ->
            when (status) {
                Status.CONNECTED -> snackbarComponent.displayMessage("tmp", currentFocus)
                Status.NOT_CONNECTED -> snackbarComponent.displayError("bite", currentFocus)
                else -> {
                }
            }
        }
    }
    fun showRoomDetailActionBar(movie: Movie?) {
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        movie?.let {
            supportActionBar?.title = movie.title
        }
    }

    private fun showMainActivityActionBar() {
        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.app_name)
    }

    override fun onBackPressed() {
        showMainActivityActionBar()
        navigator.onBackPressed()
    }
}