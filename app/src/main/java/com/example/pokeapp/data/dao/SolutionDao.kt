package com.example.pokeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokeapp.data.models.Solution
import kotlinx.coroutines.flow.Flow

@Dao
interface SolutionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(solution: Solution)

    @Update
    suspend fun update(solution: Solution)

    @Delete
    suspend fun delete(solution: Solution)

    @Query("SELECT * FROM SOLUTION ORDER BY id ASC")
    fun getAllSolutions(): Flow<List<Solution>>

    @Query("SELECT * FROM SOLUTION WHERE id = :id")
    fun getSolutionById(id: Int): Flow<Solution>

    @Query("SELECT * FROM SOLUTION WHERE question_id = :questionId")
    fun getSolutionsByQuestion(questionId: Int): Flow<List<Solution>>
}