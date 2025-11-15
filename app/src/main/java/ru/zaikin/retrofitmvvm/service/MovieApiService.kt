package ru.zaikin.retrofitmvvm.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.zaikin.retrofitmvvm.model.MovieApiResponse

interface MovieApiService {
    @GET("movie/popular")

    fun getPopularMovies(@Query("api_key") key: String) : Call<MovieApiResponse>
}