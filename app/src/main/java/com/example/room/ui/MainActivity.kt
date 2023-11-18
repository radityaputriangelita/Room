package com.example.room.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.room.database.Movie
import com.example.room.database.MovieRoomDatabase
import com.example.room.databinding.ActivityMainBinding

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


        binding.btnTambah.setOnClickListener {
            val intent = Intent(this, Form::class.java)
            startActivity(intent)
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
