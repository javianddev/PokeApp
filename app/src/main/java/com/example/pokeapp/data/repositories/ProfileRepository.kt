package com.example.pokeapp.data.repositories

import com.example.pokeapp.data.dao.ProfileDao
import com.example.pokeapp.data.models.Profile
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    fun getAllProfile() = profileDao.getAllProfiles()

    fun getProfileById(id: Int) = profileDao.getProfileById(id)

    suspend fun insertProfile(profile: Profile) = profileDao.insert(profile)

    suspend fun updateProfile(profile: Profile) = profileDao.update(profile)

    suspend fun deleteProfile(profile: Profile) = profileDao.delete(profile)


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ProfileRepository? = null

        fun getInstance(profileDao: ProfileDao) =
            instance ?: synchronized(this) {
                instance ?: ProfileRepository(profileDao).also { instance = it }
            }
    }
}