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

        //netepin tindakan saat btnHapus diklik maka akan jalanin function delete yang ada dibawah
        init {
            binding.btnHapus.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onDeleteClickListener?.onDeleteClick(ListMovie[position])
                }
            }
        }
        //nyambungin data yang ada di data class dengan tampilan
        fun bind(movie: Movie) {
            with(binding) {
                txtTitleMovie.text = movie.title
                txtDescMovie.text = movie.description
                txtTglMovie.text = movie.date
            }
        }
    }

    //taro data di adaptornya
    @SuppressLint("NotifyDataSetChanged")
    fun setData(newMovies: List<Movie>) {
        ListMovie = newMovies
        notifyDataSetChanged()
    }
    //buat respon saat ada klik tombol delete di UI
    interface OnDeleteClickListener {
        fun onDeleteClick(movie: Movie)
    }

    // untuk ngehubungin listener saat tombol klik dilakukan
    private var onDeleteClickListener: OnDeleteClickListener? = null

    //simpan listener saat tombol klik dilakukan
    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }

    //create item view holder nya saat Rv dipanggil
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ItemMovieViewHolder(binding)
    }
    //bind posisi viewholdernya jadi tiap item ada datanya
    override fun onBindViewHolder(holder: ItemMovieViewHolder, position: Int) {
        holder.bind(ListMovie[position])
    }
    //hitung jumlah datanya
    override fun getItemCount(): Int = ListMovie.size
}
