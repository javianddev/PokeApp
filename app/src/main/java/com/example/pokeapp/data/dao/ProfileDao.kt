package com.example.pokeapp.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pokeapp.data.models.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: Profile)

    @Update
    suspend fun update(profile: Profile)

    @Delete
    suspend fun delete(profile: Profile)

    @Query("SELECT * FROM PROFILE WHERE ID = :id") //Es obvio que solo hay uno en esta app, pero por si amplío
    fun getProfileById(id: Int): Flow<Profile>

    @Query("SELECT * FROM PROFILE ORDER BY ID DESC")
    fun getAllProfiles(): Flow<List<Profile>> //Es obvio que solo hay uno en esta app, pero por si amplío

}