package unitTest.fake

import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.remotedata.model.PokemonInfo
import com.example.pokeapp.remotedata.model.PokemonResponse
import com.example.pokeapp.remotedata.model.Result
import java.time.LocalDate

object FakeDataSource {
    val pokemonResponse =
        PokemonResponse(
            count = 1302,
            next = "https://pokeapi.co/api/v2/pokemon/?offset=1&limit=1",
            previous = "",
            results = listOf(
                Result(
                    name = "bulbasaur",
                    url = "https://pokeapi.co/api/v2/pokemon/1/"
                ),
                Result(
                    name = "ivysaur",
                    url = "https://pokeapi.co/api/v2/pokemon/2/"
                ),
            )
        )
    val trainerResponse =
        Trainer(
            id = 1,
            name = "Javier",
            birthdate = LocalDate.now().minusYears(18),
            birthplace = ""
        )
}