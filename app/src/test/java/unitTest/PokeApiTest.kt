package unitTest

import com.example.pokeapp.remotedata.repositories.PokedexRepository
import com.example.pokeapp.remotedata.services.ApiPokemonService
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import unitTest.fake.FakeDataSource

@RunWith(MockitoJUnitRunner::class)
class PokeApiTest {

    @Mock
    private lateinit var apiPokemonService: ApiPokemonService

    @InjectMocks
    lateinit var pokedexRepository: PokedexRepository
    @Test
    fun testGetPokemons() = runTest {
        //Llamo a la api, pero solo cojo el primer objeto que me llegaria
        val limit = 1
        val offset = 0
        //Simulo el objeto que me llegaria
        val pokemonResponse = FakeDataSource.pokemonResponse
        `when`(apiPokemonService.getPokemon(limit, offset)).thenReturn(pokemonResponse)

        //Llamo a la función del repositorio deseada...
        val result = pokedexRepository.getPokemons(limit, offset)

        //Verifico que el resultado sea el esperado
        assertNotNull(result)
        assertEquals(result, pokemonResponse)
    }

    /*@Test
    fun testGetPokemonInfo() = runTest {
        val pokemonInfo = FakeDataSource.pokemonInfo
        `when`(apiPokemonService.getPokemonInfo(1)).thenReturn(pokemonInfo)
        val result = pokedexRepository.getPokemonInfo(1)
        assertNotNull(result)
        assertEquals(result, pokemonInfo)

    }*/

}