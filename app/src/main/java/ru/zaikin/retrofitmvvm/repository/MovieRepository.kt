package ru.zaikin.retrofitmvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
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
}
