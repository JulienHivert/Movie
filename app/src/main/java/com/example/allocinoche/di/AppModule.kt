package com.example.allocinoche.di

import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.allocinoche.db.MovieDB
import com.example.allocinoche.db.MovieDBImpl
import com.example.allocinoche.presentation.activity.MainActivity
import com.example.allocinoche.navigator.MainNavigator
import com.example.allocinoche.viewmodel.MovieViewModel
import com.example.allocinoche.network.Webservice
import com.example.allocinoche.network.WebserviceImpl
import com.example.allocinoche.presentation.component.ISnackbar
import com.example.allocinoche.presentation.component.SnackbarComponent
import com.example.allocinoche.repository.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    scope(named<MainActivity>()) {
        scoped { (activity: AppCompatActivity) -> MainNavigator(activity)
        }
    }
    single { WebserviceMovieRepoImpl(get()) as WebserviceMovieRepo }
    single { DatabaseMovieRepoImpl(get()) as DatabaseMovieRepo }
    single { WebserviceImpl("https://api.themoviedb.org/3/") as Webservice }
    single { Room.databaseBuilder(androidContext(), MovieDBImpl::class.java, "movie-database").build() as MovieDB}

    viewModel { MovieViewModel(get(), get()) }
    factory { SnackbarComponent(get()) as ISnackbar }
}

val alloCinocheApp = listOf(appModule)