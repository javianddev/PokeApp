package com.example.pokeapp.daoTest

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.pokeapp.data.AppDatabase
import com.example.pokeapp.data.dao.PokemonDao
import com.example.pokeapp.data.dao.QuestionDao
import com.example.pokeapp.data.dao.RegionDao
import com.example.pokeapp.data.dao.SolutionDao
import com.example.pokeapp.data.dao.TrainerDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DaoTest {

    private lateinit var questionDao: QuestionDao
    private lateinit var solutionDao: SolutionDao
    private lateinit var regionDao: RegionDao
    private lateinit var pokemonDao: PokemonDao
    private lateinit var trainerDao: TrainerDao
    private lateinit var pokeDb: AppDatabase

    @Before
    fun createDb(){
        val context: Context = ApplicationProvider.getApplicationContext()
        pokeDb = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        questionDao = pokeDb.questionDao()
        solutionDao = pokeDb.solutionDao()
        regionDao = pokeDb.regionDao()
        pokemonDao = pokeDb.pokemonDao()
        trainerDao = pokeDb.trainerDao()
        //medalDao = pokeDb.medalDao() //Aún sin relevancia en el código
    }

    //Probamos en orden según Foreign Keys
    //El primer Dao que hay lo usaremos para mostrar una prueba de todas las funciones, independientemente de su uso
    //Luego probaremos únicamente las funciones que utilizamos en el proyecto
    @After
    @Throws(IOException::class)
    fun closeDb() {
        pokeDb.close()
    }

    /****************************************** POKEMON DAO ****************************************************/
    //Hacemos una prueba de todas las funciones como ejemplo
    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsPokemonIntoDB() = runBlocking {
        pokemonDao.insert(pokemon1)
        val allPokemon = pokemonDao.getAllPokemon().first()
        assertEquals(allPokemon[0], pokemon1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllPokemon_returnsAllPokemonFromDB() = runBlocking {
        pokemonDao.insert(pokemon1)
        pokemonDao.insert(pokemon2)
        pokemonDao.insert(pokemon3)
        val allPokemon = pokemonDao.getAllPokemon().first()
        assertEquals(allPokemon[0], pokemon2) //Comprobamos el onConflic.Replace
        assertEquals(allPokemon[1], pokemon3) //se inserta uno nuevo
    }

    @Test
    @Throws(Exception::class)
    fun daoGetOnePokemon_returnsOnePokemonFromDB() = runBlocking {
        pokemonDao.insert(pokemon1)
        val pokemon = pokemonDao.getPokemonById(1).first()
        assertEquals(pokemon, pokemon1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetOnePokemonByPokedexId_returnsOnePokemonByPokedexIdFromDB() = runBlocking {
        pokemonDao.insert(pokemon2)
        val pokemon = pokemonDao.getPokemonByPokedexId(47).first()
        assertEquals(pokemon, pokemon2)
    }

    @Test
    @Throws(Exception::class)
    fun daoDeletePokemon_deletePokemonFromDB() = runBlocking {
        pokemonDao.insert(pokemon1)
        pokemonDao.delete(pokemon1)
        val allPokemon = pokemonDao.getAllPokemon().first()
        assertTrue(allPokemon.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdatePokemon_updatePokemonFromDB() = runBlocking {
        pokemonDao.insert(pokemon1)
        pokemonDao.update(pokemon2) //Tienen mismo id
        val pokemon = pokemonDao.getPokemonById(1).first()
        assertEquals(pokemon, pokemon2)
    }

    /****************************************** TRAINER DAO ****************************************************/

    @Test
    @Throws(Exception::class)
    fun daoInsertTrainer_returnTrainerByIdFromDB() = runBlocking {
        trainerDao.insert(trainer1)
        val trainer = trainerDao.getTrainerById(1).first()
        assertEquals(trainer, trainer1)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateTrainer_returnTrainerByIdFromDB() = runBlocking {
        trainerDao.insert(trainer1)
        trainerDao.update(trainer2)
        val trainer = trainerDao.getTrainerById(1).first()
        assertEquals(trainer, trainer2)
    }

    /****************************************** REGION DAO ****************************************************/

    @Test
    @Throws(Exception::class)
    fun daoInsertRegion_returnAllRegionsFromDB() = runBlocking {
        regionDao.insert(region1)
        regionDao.insert(region2)
        val allRegions = regionDao.getAllRegion().first()
        assertEquals(allRegions[0], region1)
        assertEquals(allRegions[1], region2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateRegionMedal_updateOneRegionMedalAndReturnOneRegionFromDB() = runBlocking{
        regionDao.insert(region1)
        regionDao.updateRegionMedal(1, true)
        val region = regionDao.getRegionById(1).first()
        assertEquals(region, region3) //region3 es igual que region1 pero en true
    }

    /****************************************** QUESTION DAO ****************************************************/

    @Test
    @Throws(Exception::class)
    fun daoInsertQuestion_getQuestionByRegionIdFromDB() = runBlocking {
        regionDao.insert(region1)
        questionDao.insert(question1)
        val question = questionDao.getQuestionsByRegionId(1).first()
        assertEquals(question[0], question1)
    }


    /****************************************** SOLUTION DAO ****************************************************/
    @Test
    @Throws(Exception::class)
    fun daoInsertSolution_returnSolutionToDB() = runBlocking {
        regionDao.insert(region1)
        questionDao.insert(question1)
        solutionDao.insert(solution1)
        solutionDao.insert(solution2)
        val allSolutions = solutionDao.getAllSolutions().first()
        assertEquals(allSolutions[0], solution1)
        assertEquals(allSolutions[1], solution2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsertSolution_returnSolutionByQuestionIdFromDB() = runBlocking {
        regionDao.insert(region1)
        questionDao.insert(question1)
        solutionDao.insert(solution1)
        solutionDao.insert(solution2)
        val allSolutions = solutionDao.getSolutionsByQuestion(1).first()
        assertEquals(allSolutions[0], solution1)
        assertEquals(allSolutions[1], solution2)
    }



}