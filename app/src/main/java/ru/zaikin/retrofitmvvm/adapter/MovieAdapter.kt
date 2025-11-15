package ru.zaikin.retrofitmvvm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.zaikin.retrofitmvvm.R
import ru.zaikin.retrofitmvvm.databinding.MovieItemBinding
import ru.zaikin.retrofitmvvm.model.Movie
import ru.zaikin.retrofitmvvm.ui.MovieDetailsActivity

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val movies = mutableListOf<Movie>()

    fun submitList(newMovies: List<Movie>) {
        movies.apply {
            clear()
            addAll(newMovies)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

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
}
