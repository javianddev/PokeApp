package com.example.pokeapp.data.repositories

import com.example.pokeapp.data.dao.TrainerDao
import com.example.pokeapp.data.models.Trainer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainerRepository @Inject constructor(private val trainerDao: TrainerDao) {

    fun getAllTrainer() = trainerDao.getAllTrainers()
    fun getTrainerById(id: Int) = trainerDao.getTrainerById(id)
    suspend fun insertTrainer(trainer: Trainer) = trainerDao.insert(trainer)
    suspend fun updateTrainer(trainer: Trainer) = trainerDao.update(trainer)
    suspend fun deleteTrainer(trainer: Trainer) = trainerDao.delete(trainer)


    companion object {

        // For Singleton instantiation
        @Volatile private var instance: TrainerRepository? = null

        fun getInstance(trainerDao: TrainerDao) =
            instance ?: synchronized(this) {
                instance ?: TrainerRepository(trainerDao).also { instance = it }
            }
    }
}