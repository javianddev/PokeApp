package com.example.pokeapp.data.repositories

import com.example.pokeapp.data.dao.MedalDao
import com.example.pokeapp.data.models.Medal
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MedalRepositry @Inject constructor(private val medalDao: MedalDao) {

    fun getAllMedals() = medalDao.getAllMedals()
    fun getMedalById(id: Int) = medalDao.getMedalById(id)
    fun getMedalsByRegionId(regionId: Int) = medalDao.getMedalsByRegionId(regionId)
    suspend fun insertMedal(medal: Medal) = medalDao.insert(medal)
    suspend fun updateMedal(medal: Medal) = medalDao.update(medal)
    suspend fun deleteMedal(medal: Medal) = medalDao.delete(medal)

}