package com.example.pokeapp.data.repositories

import com.example.pokeapp.data.dao.RegionDao
import com.example.pokeapp.data.models.Region
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegionRepository @Inject constructor(private val regionDao: RegionDao){

    fun getAllRegion() = regionDao.getAllRegion()

    fun getRegionById(id: Int) = regionDao.getRegionById(id)

    suspend fun insertRegion(region: Region) = regionDao.insert(region)

    suspend fun updateRegion(region: Region) = regionDao.update(region)

    suspend fun deleteRegion(region: Region) = regionDao.delete(region)

}