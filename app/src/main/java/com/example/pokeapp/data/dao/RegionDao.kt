package com.example.pokeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokeapp.data.models.Region
import kotlinx.coroutines.flow.Flow

@Dao
interface RegionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(region: Region)

    @Update
    suspend fun update(region: Region)

    @Delete
    suspend fun delete(region: Region)

    @Query("SELECT * FROM REGION ORDER BY id ASC")
    fun getAllRegion(): Flow<List<Region>>

    @Query("SELECT * FROM REGION WHERE id = :id")
    fun getRegionById(id: Int): Flow<Region>
}