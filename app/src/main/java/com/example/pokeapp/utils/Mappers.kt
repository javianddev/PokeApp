package com.example.pokeapp.utils


import androidx.compose.ui.graphics.Color
import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.data.models.PokemonDetail
import com.example.pokeapp.remotedata.model.PokemonInfo
import com.example.pokeapp.remotedata.model.PokemonResponse
import com.example.pokeapp.remotedata.model.Type
import com.example.pokeapp.ui.theme.TypeBug
import com.example.pokeapp.ui.theme.TypeDark
import com.example.pokeapp.ui.theme.TypeDragon
import com.example.pokeapp.ui.theme.TypeElectric
import com.example.pokeapp.ui.theme.TypeFairy
import com.example.pokeapp.ui.theme.TypeFighting
import com.example.pokeapp.ui.theme.TypeFire
import com.example.pokeapp.ui.theme.TypeFlying
import com.example.pokeapp.ui.theme.TypeGhost
import com.example.pokeapp.ui.theme.TypeGrass
import com.example.pokeapp.ui.theme.TypeGround
import com.example.pokeapp.ui.theme.TypeIce
import com.example.pokeapp.ui.theme.TypeNormal
import com.example.pokeapp.ui.theme.TypePoison
import com.example.pokeapp.ui.theme.TypePsychic
import com.example.pokeapp.ui.theme.TypeRock
import com.example.pokeapp.ui.theme.TypeSteel
import com.example.pokeapp.ui.theme.TypeWater
import com.example.pokeapp.viewmodels.EditTrainerUiState

fun EditTrainerUiState.toTrainer(): Trainer = Trainer(
    id = 1, //en un principio solo va a haber un perfil, así que lo aguanto con el id a 1
    name = name,
    birthdate = birthdate,
    birthplace = birthplace,
)

fun mapPokemonResToPokemon(pokemonResponse: PokemonResponse): List<Pokemon> {
    return pokemonResponse.results.map{result ->
        val id = if(result.url.endsWith("/")) {
            result.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            result.url.takeLastWhile { it.isDigit() }
        }
        Pokemon(
            id = id,
            name= result.name,
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png", //En PokeApi, los sprites van con esta URL
        )
    }
}

fun PokemonInfo.toPokemonDetail(): PokemonDetail = PokemonDetail(
    id = id,
    name = name,
    imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png",
    stats = stats,
    types = types,
    height = height,
    weight = weight
)

fun getColorType(type: Type): Color {
    return when(type.type.name) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}
