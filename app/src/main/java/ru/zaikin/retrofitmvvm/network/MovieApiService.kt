package ru.zaikin.retrofitmvvm.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.zaikin.retrofitmvvm.model.MovieApiResponse

interface MovieApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): MovieApiResponse

    @GET("movie/popular")
    suspend fun getPopularMoviesWithPaging(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieApiResponse
}
