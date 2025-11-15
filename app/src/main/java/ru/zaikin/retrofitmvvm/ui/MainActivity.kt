package ru.zaikin.retrofitmvvm.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import ru.zaikin.retrofitmvvm.R
import ru.zaikin.retrofitmvvm.databinding.ActivityMainBinding
import ru.zaikin.retrofitmvvm.viewmodel.MainActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.zaikin.retrofitmvvm.adapter.MoviePagingAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: MoviePagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        adapter = MoviePagingAdapter()
        binding.recyclerView.adapter = adapter
        setRecyclerLayout()

        binding.swiperefresh.setColorSchemeResources(R.color.black)
        binding.swiperefresh.setOnRefreshListener {
            adapter.refresh()
        }

        observeMovies()
    }

    private fun setRecyclerLayout() {
        val spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4
        binding.recyclerView.layoutManager = GridLayoutManager(this, spanCount)
    }

    private fun observeMovies() {
        val apiKey = getString(R.string.api_key)

        lifecycleScope.launch {
            viewModel.getMoviesPaging(apiKey).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.swiperefresh.isRefreshing = loadState.refresh is LoadState.Loading
        }
    }
}
