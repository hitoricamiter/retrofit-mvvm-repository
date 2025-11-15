package ru.zaikin.retrofitmvvm.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import ru.zaikin.retrofitmvvm.R
import ru.zaikin.retrofitmvvm.adapter.MovieAdapter
import ru.zaikin.retrofitmvvm.databinding.ActivityMainBinding
import ru.zaikin.retrofitmvvm.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: MovieAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        adapter = MovieAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        setRecyclerLayout()

        binding.swiperefresh.setColorSchemeResources(R.color.black)
        binding.swiperefresh.setOnRefreshListener {
            fetchMovies()
        }

        observeMovies()
        fetchMovies()
    }

    private fun setRecyclerLayout() {
        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
        binding.recyclerView.layoutManager = GridLayoutManager(this, spanCount)
    }

    private fun observeMovies() {
        viewModel.getAllMovies(getString(R.string.api_key)).observe(this) { movieApiResponse ->
            movieApiResponse?.results?.let { movies ->
                adapter.submitList(movies)
                binding.swiperefresh.isRefreshing = false
            }
        }
    }

    private fun fetchMovies() {
        binding.swiperefresh.isRefreshing = true
        viewModel.getAllMovies(getString(R.string.api_key))
    }
}
