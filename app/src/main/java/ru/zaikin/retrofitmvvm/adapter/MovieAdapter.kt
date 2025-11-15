package ru.zaikin.retrofitmvvm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.zaikin.retrofitmvvm.databinding.MovieItemBinding
import ru.zaikin.retrofitmvvm.model.Movie
import ru.zaikin.retrofitmvvm.ui.MovieDetailsActivity
import ru.zaikin.retrofitmvvm.R

class MoviePagingAdapter :
    PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHolder>(MOVIE_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                titleTextView.text = movie.title
                popularityTextView.text = movie.popularity.toString()

                Glide.with(root.context)
                    .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                    .placeholder(R.drawable.progress_circle)
                    .into(posterView)

                root.setOnClickListener {
                    val intent = Intent(root.context, MovieDetailsActivity::class.java)
                    intent.putExtra("MOVIE_DATA", movie)
                    root.context.startActivity(intent)
                }
            }
        }
    }

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}
