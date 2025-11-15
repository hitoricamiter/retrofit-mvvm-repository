package ru.zaikin.retrofitmvvm.view

import android.content.Intent
import android.icu.text.CaseMap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import ru.zaikin.retrofitmvvm.R
import ru.zaikin.retrofitmvvm.model.Movie

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var movie: Movie
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var posterPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_movie_details)

        poster = findViewById(R.id.poster)
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)

        val intent: Intent = intent

        if (intent.hasExtra("MOVIE_DATA")) {
            val movie = intent.getParcelableExtra<Movie>("MOVIE_DATA")

            title.text = movie?.title
            description.text = movie?.originalLanguage
            posterPath = movie?.posterPath.toString()
            val imagePath = "https://image.tmdb.org/t/p/w500/$posterPath"

            Glide.with(this)
                .load(imagePath)
                .placeholder(R.drawable.progress_circle)
                .into(poster)

        }
    }
}