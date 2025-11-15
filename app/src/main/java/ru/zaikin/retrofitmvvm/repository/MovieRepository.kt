package ru.zaikin.retrofitmvvm.repository

import ru.zaikin.retrofitmvvm.data.MoviePagingSource
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ru.zaikin.retrofitmvvm.model.Movie
import ru.zaikin.retrofitmvvm.model.MovieApiResponse
import ru.zaikin.retrofitmvvm.network.RetrofitInstance

class MovieRepository {

    private val apiService = RetrofitInstance.apiService

    fun getMovies(apiKey: String): LiveData<MovieApiResponse> = liveData {
        try {
            val response = apiService.getPopularMovies(apiKey)
            emit(response)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getMoviesPaging(apiKey: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,   // размер страницы
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(apiService, apiKey) }
        ).flow
    }
}
