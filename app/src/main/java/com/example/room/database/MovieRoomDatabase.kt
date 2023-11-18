package com.example.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//iniasasi entitas di Movie
@Database(entities = [Movie::class], version = 2, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {
    // MovieDao untuk CRUD
    abstract fun movieDao(): MovieDao?

    companion object {
        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context): MovieRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(MovieRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MovieRoomDatabase::class.java, "movie_database"
                    )
                        // Tambahkan migrasi dari versi 2 ke versi 1 di sini
                        .addMigrations(MIGRATION_2_1)
                        .build()
                }
            }
            return INSTANCE
        }

        // Migrasi dari versi 2 ke versi 1
        private val MIGRATION_2_1: Migration = object : Migration(2, 1) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Logika migrasi dari versi 2 ke versi 1
                // Misalnya, alter table, tambahkan kolom, dll.
            }
        }
    }
}
