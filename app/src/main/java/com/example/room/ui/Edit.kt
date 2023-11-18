package com.example.room.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.room.R
import com.example.room.database.Movie
import com.example.room.database.MovieRoomDatabase
import com.example.room.databinding.ActivityEditBinding
import com.example.room.databinding.ActivityFormBinding

class Edit : AppCompatActivity() {

    private lateinit var binding: ActivityEditBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movie_id", 0)
        val movieTitle = intent.getStringExtra("movie_title")
        val movieDescription = intent.getStringExtra("movie_description")
        val movieDate = intent.getStringExtra("movie_date")

        // Set data to edit texts in the edit form
        binding.editTextTitle.setText(movieTitle)
        binding.editTextDesc.setText(movieDescription)
        binding.editTextDate.setText(movieDate)

        // Implement edit functionality when needed
        // For example, when a "Save" button is clicked to update the data
        binding.buttonUpdate.setOnClickListener {
            // Get updated data from EditText fields
            val updatedTitle = binding.editTextTitle.text.toString()
            val updatedDesc = binding.editTextDesc.text.toString()
            val updatedDate = binding.editTextDate.text.toString()

            // Handle update operation, e.g., using Room database
            // For example, if you have MovieDao and MovieRoomDatabase:
            val db = MovieRoomDatabase.getDatabase(this)
            val movieDao = db?.movieDao()

            // Create a Movie object with updated data
            val updatedMovie = Movie(movieId, updatedTitle, updatedDesc, updatedDate)

            // Update the movie in the database
            movieDao?.update(updatedMovie)

            // Optionally, you can finish this activity or navigate back to MainActivity
            // For instance:
            finish()
        }
    }
}