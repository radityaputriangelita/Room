package com.example.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

//iniasasi entitas di Movie
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {
    //MovieDao untuk CRUD
    abstract fun movieDao(): MovieDao?

    //database
    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        //function akses database
        fun getDatabase(context: Context): MovieRoomDatabase? {
            //cek database kodong apa engga
            //kalo datanya ga ada pake instance kalo ada nanti datanya keluar
            if (INSTANCE == null) {
                synchronized(MovieRoomDatabase::class.java) {
                    //builder() bikin instance nya dengan nama "movie_database"
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        MovieRoomDatabase::class.java, "movie_database"
                    )
                        .build()
                }
            }
            return INSTANCE
        }
    }
}