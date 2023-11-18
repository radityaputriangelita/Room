package com.example.room.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.room.database.Movie
import com.example.room.database.MovieDao
import com.example.room.database.MovieRoomDatabase
import com.example.room.databinding.ActivityFormBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Form : AppCompatActivity() {
    private lateinit var binding: ActivityFormBinding
    private lateinit var movieDao : MovieDao
    private lateinit var executorService: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //operasi databse dari therad yg beda dari yang utama
        executorService = Executors.newSingleThreadExecutor()

        //buat objeknya nanti
        val db = MovieRoomDatabase.getDatabase(this)
        //ngambil data dari database kaya di movie dao
        if (db != null) {
            movieDao = db.movieDao()!!
        }

        //binding inisiasi saat buttonnya di klik.
        binding.buttonSave.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val description = binding.editTextDesc.text.toString()
            val date = binding.editTextDate.text.toString()

            //cek datanya ga kosong
            if (title.isNotEmpty() && description.isNotEmpty() && date.isNotEmpty()) {
                val note = Movie(
                    title = title,
                    description = description,
                    date = date
                )
                insert(note)
            } else {
                //munculin toast
                Toast.makeText(applicationContext, "Please fill the form correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insert(movie: Movie) {
        executorService.execute {
            movieDao.insert(movie)
            // Balik ke Main Activity
            setResult(RESULT_OK)
            finish() // close form nya
        }
    }
}
