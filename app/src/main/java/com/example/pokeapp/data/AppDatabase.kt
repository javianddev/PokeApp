package com.example.pokeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokeapp.data.dao.ProfileDao
import com.example.pokeapp.data.models.Profile

@Database(entities = arrayOf(Profile::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    companion object {

        @Volatile
        var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "pokemon_database"
                )
                    .build()
                    .also{Instance = it}
            }
        }
    }

}