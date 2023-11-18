package com.example.room.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.room.database.Movie
import com.example.room.databinding.ItemMovieBinding

class ListMovieAdapter(private var ListMovie: List<Movie>) :
    RecyclerView.Adapter<ListMovieAdapter.ItemMovieViewHolder>() {

    inner class ItemMovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            with(binding) {
                txtTitleMovie.text = movie.title
                txtDescMovie.text = movie.description
                txtTglMovie.text = movie.date
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newMovies: List<Movie>) {
        ListMovie = newMovies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemMovieViewHolder, position: Int) {
        holder.bind(ListMovie[position])
    }

    override fun getItemCount(): Int = ListMovie.size
}
