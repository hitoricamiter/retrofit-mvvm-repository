package ru.zaikin.retrofitmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.zaikin.retrofitmvvm.model.MovieApiResponse
import ru.zaikin.retrofitmvvm.repository.MovieRepository

class MainActivityViewModel : ViewModel() {

    private val repository = MovieRepository()

    fun getAllMovies(apiKey: String): LiveData<MovieApiResponse> {
        return repository.getMovies(apiKey)
    }
}
