package com.example.pokeapp.data.repositories

import com.example.pokeapp.data.dao.SolutionDao
import com.example.pokeapp.data.models.Solution
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SolutionRepository @Inject constructor(private val solutionDao: SolutionDao) {

    fun getAllSolutions() = solutionDao.getAllSolutions()
    fun getSolutionById(id: Int) = solutionDao.getSolutionById(id)
    fun getSolutionsByQuestionId(questionId: Int) = solutionDao.getSolutionsByQuestion(questionId)
    suspend fun insertSolution(solution: Solution) = solutionDao.insert(solution)
    suspend fun updateSolution(solution: Solution) = solutionDao.update(solution)
    suspend fun deleteSolution(solution: Solution) = solutionDao.delete(solution)

}