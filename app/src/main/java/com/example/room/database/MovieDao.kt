package com.example.room.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Movie)
    @Update
    fun update(note: Movie)
    @Delete
    fun delete(note: Movie)
    @get:Query("SELECT * from movie_table ORDER BY id ASC")
    val allNotes: LiveData<List<Movie>>
}