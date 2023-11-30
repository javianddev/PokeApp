package com.example.pokeapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokeapp.data.converters.DateConverter
import com.example.pokeapp.data.dao.MedalDao
import com.example.pokeapp.data.dao.QuestionDao
import com.example.pokeapp.data.dao.RegionDao
import com.example.pokeapp.data.dao.SolutionDao
import com.example.pokeapp.data.dao.TrainerDao
import com.example.pokeapp.data.models.Medal
import com.example.pokeapp.data.models.Question
import com.example.pokeapp.data.models.Region
import com.example.pokeapp.data.models.Solution
import com.example.pokeapp.data.models.Trainer

@Database(entities = [Trainer::class, Region::class, Question::class, Solution::class, Medal::class], version = 5, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun trainerDao(): TrainerDao
    abstract fun regionDao(): RegionDao
    abstract fun questionDao(): QuestionDao
    abstract fun solutionDao(): SolutionDao
    abstract fun medalDao(): MedalDao

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
                    .fallbackToDestructiveMigration()
                    .build()
                    .also{Instance = it}
            }
        }
    }

}