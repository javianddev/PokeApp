package com.example.pokeapp.data.repositories

import com.example.pokeapp.data.dao.QuestionDao
import com.example.pokeapp.data.models.Question
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionRepository @Inject constructor(private val questionDao: QuestionDao) {

    fun getAllQuestions() = questionDao.getAllQuestions()
    fun getQuestionById(id: Int) = questionDao.getQuestionById(id)
    fun getQuestionsByRegionId(regionId: Int) = questionDao.getQuestionsByRegionId(regionId)
    suspend fun insertQuestion(question:Question) = questionDao.insert(question)
    suspend fun updateQuestion(question: Question) = questionDao.update(question)
    suspend fun deleteQestion(question: Question) = questionDao.delete(question)
}