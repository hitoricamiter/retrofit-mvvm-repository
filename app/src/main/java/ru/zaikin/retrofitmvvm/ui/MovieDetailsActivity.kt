package ru.zaikin.retrofitmvvm.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import ru.zaikin.retrofitmvvm.R
import ru.zaikin.retrofitmvvm.databinding.ActivityMovieDetailsBinding
import ru.zaikin.retrofitmvvm.model.Movie

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_details)

        intent.getParcelableExtra<Movie>("MOVIE_DATA")?.let { movie ->
            bindMovie(movie)
        }
    }

    private fun bindMovie(movie: Movie) {
        binding.apply {
            title.text = movie.title
            description.text = movie.originalLanguage
            val imageUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"

            Glide.with(root.context)
                .load(imageUrl)
                .placeholder(R.drawable.progress_circle)
                .into(poster)
        }
    }
}
