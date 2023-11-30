package com.example.pokeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokeapp.data.models.Medal
import com.example.pokeapp.data.models.Question
import kotlinx.coroutines.flow.Flow

@Dao
interface MedalDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medal: Medal)

    @Update
    suspend fun update(medal: Medal)

    @Delete
    suspend fun delete(medal: Medal)

    @Query("SELECT * FROM MEDAL ORDER BY id ASC")
    fun getAllMedals(): Flow<List<Medal>>

    @Query("SELECT * FROM MEDAL WHERE id= :id")
    fun getMedalById(id: Int): Flow<Medal>

    @Query("SELECT * FROM MEDAL WHERE region_id = :regionId")
    fun getMedalsByRegionId(regionId: Int): Flow<List<Medal>>

}