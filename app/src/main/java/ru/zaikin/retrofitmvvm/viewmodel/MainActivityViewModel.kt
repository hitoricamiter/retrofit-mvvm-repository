package ru.zaikin.retrofitmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import ru.zaikin.retrofitmvvm.model.Movie
import ru.zaikin.retrofitmvvm.model.MovieApiResponse
import ru.zaikin.retrofitmvvm.repository.MovieRepository

class MainActivityViewModel : ViewModel() {

    private val repository = MovieRepository()

    fun getAllMovies(apiKey: String): LiveData<MovieApiResponse> {
        return repository.getMovies(apiKey)
    }

    fun getMoviesPaging(apiKey: String): Flow<PagingData<Movie>> {
        return repository.getMoviesPaging(apiKey)
            .cachedIn(viewModelScope)
    }
}
