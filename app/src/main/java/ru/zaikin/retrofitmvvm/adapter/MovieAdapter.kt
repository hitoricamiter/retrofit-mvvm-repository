package ru.zaikin.retrofitmvvm.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ru.zaikin.retrofitmvvm.model.Movie
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.zaikin.retrofitmvvm.R
import ru.zaikin.retrofitmvvm.view.MovieDetailsActivity

data class MovieAdapter(var movies: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MovieViewHolder,
        position: Int
    ) {
        holder.title.text = movies[position].title
        holder.population.text = movies[position].popularity.toString()

        val concretePath = movies[position].posterPath
        val imagePath = "https://image.tmdb.org/t/p/w500/$concretePath"

        Glide.with(holder.itemView.context).load(imagePath).placeholder(R.drawable.progress_circle)
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return movies.size
    }


    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById<TextView>(R.id.titleTextView)
        var population: TextView = itemView.findViewById<TextView>(R.id.popularityTextView)
        var image: ImageView = itemView.findViewById<ImageView>(R.id.posterView)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val movie: Movie = movies[position]
                    val intent: Intent = Intent(itemView.context, MovieDetailsActivity::class.java)
                    intent.putExtra("MOVIE_DATA", movie)
                    itemView.context.startActivity(intent)


                }

            }
        }

    }
}