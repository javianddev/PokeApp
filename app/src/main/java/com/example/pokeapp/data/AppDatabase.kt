package com.example.pokeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokeapp.data.converters.DateConverter
import com.example.pokeapp.data.dao.TrainerDao
import com.example.pokeapp.data.models.Trainer

@Database(entities = [Trainer::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun trainerDao(): TrainerDao

    companion object {

        @Volatile
        var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "poke_db"
                )
                    .createFromAsset("database/pokedb.db")
                    .build()
                    .also{Instance = it}
            }
        }
    }

}