package com.example.room.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.database.Movie
import com.example.room.database.MovieRoomDatabase
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listMovieAdapter: ListMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        // ngambil data dari database
        val db = MovieRoomDatabase.getDatabase(this)
        val movieDao = db?.movieDao()

        // ambil semua data, dia diinisiasikan di MovieRoomDatabase
        val allMovies: LiveData<List<Movie>>? = movieDao?.allNotes

        // taro data room ke adaptor
        allMovies?.observe(this) { movies ->
            movies?.let { listMovieAdapter.setData(it) }
        }
        // Atur listener delete di adapter
        listMovieAdapter.setOnDeleteClickListener(object : ListMovieAdapter.OnDeleteClickListener {
            override fun onDeleteClick(movie: Movie) {
                // hapus data di latar belakang
                deleteMovieInBackground(movie)
            }
        })

        //intent button tambah ke form
        binding.btnTambah.setOnClickListener {
            val intent = Intent(this, Form::class.java)
            startActivity(intent)
        }
    }

    //background penghapusan ke data Dao Nya pake delete yang ada di Movie Dao
    private fun deleteMovieInBackground(movie: Movie) {
        val movieDao = MovieRoomDatabase.getDatabase(this)?.movieDao()
        movieDao?.let {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    // Akses database di latar belakang
                    it.delete(movie)
                }
            }
        }
    }
    private fun setupRecyclerView() {
        //buat dulu intance gitu kosong dulu
        listMovieAdapter = ListMovieAdapter(emptyList())
        binding.rvMovie.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listMovieAdapter
        }
    }
}
