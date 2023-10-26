package com.example.pokeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokeapp.data.models.Trainer
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: Trainer)

    @Update
    suspend fun update(profile: Trainer)

    @Delete
    suspend fun delete(profile: Trainer)

    @Query("SELECT * FROM TRAINER WHERE ID = :id") //Es obvio que solo hay uno en esta app, pero por si amplío
    fun getTrainerById(id: Int): Flow<Trainer>

    @Query("SELECT * FROM TRAINER ORDER BY ID DESC")
    fun getAllTrainers(): Flow<List<Trainer>> //Es obvio que solo hay uno en esta app, pero por si amplío

}