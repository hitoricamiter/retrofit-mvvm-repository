package ru.zaikin.retrofitmvvm.view

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.zaikin.retrofitmvvm.R
import ru.zaikin.retrofitmvvm.adapter.MovieAdapter
import ru.zaikin.retrofitmvvm.model.Movie
import ru.zaikin.retrofitmvvm.model.MovieApiResponse
import ru.zaikin.retrofitmvvm.service.MovieApiService
import ru.zaikin.retrofitmvvm.service.RetrofitInstance

class MainActivity : AppCompatActivity() {

    private lateinit var results: ArrayList<Movie>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private lateinit var swipe: SwipeRefreshLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        swipe = findViewById(R.id.swiperefresh)
        swipe.setColorSchemeResources(R.color.black)
        swipe.setOnRefreshListener {
            getPopularMovies()
        }

        getPopularMovies()

    }

    fun getPopularMovies() {
        val movieApi: MovieApiService = RetrofitInstance.apiService
        val call: Call<MovieApiResponse> = movieApi.getPopularMovies(getString(R.string.api_key))

        call.enqueue(object : Callback<MovieApiResponse> {
            override fun onResponse(
                call: Call<MovieApiResponse?>,
                response: Response<MovieApiResponse?>
            ) {
                val movieApiResponse: MovieApiResponse? = response.body()

                if (movieApiResponse != null) {
                    results = movieApiResponse.results as ArrayList<Movie>
                }

                recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                adapter = MovieAdapter(results)


                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    val layout = GridLayoutManager(this@MainActivity, 2)
                    recyclerView.layoutManager = layout
                } else {
                    val layout = GridLayoutManager(this@MainActivity, 4)
                    recyclerView.layoutManager = layout
                }

                recyclerView.itemAnimator = DefaultItemAnimator()
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(
                call: Call<MovieApiResponse?>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })

    }

}