package com.example.pokeapp.utils


import androidx.compose.ui.graphics.Color
import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.data.models.PokemonDetail
import com.example.pokeapp.remotedata.model.PokemonInfo
import com.example.pokeapp.remotedata.model.PokemonResponse
import com.example.pokeapp.remotedata.model.Type
import com.example.pokeapp.ui.theme.BadStat
import com.example.pokeapp.ui.theme.MasterStat
import com.example.pokeapp.ui.theme.MaxiStat
import com.example.pokeapp.ui.theme.MegaStat
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
import com.example.pokeapp.ui.theme.WorstStat
import com.example.pokeapp.ui.theme.RegularStat
import com.example.pokeapp.ui.theme.NormalStat
import com.example.pokeapp.ui.theme.GoodStat
import com.example.pokeapp.ui.theme.NiceStat
import com.example.pokeapp.ui.theme.SuperStat
import com.example.pokeapp.ui.theme.HiperStat
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

fun getTypeColor(type: Type): Color {
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

fun getStatColor(baseStat: Int):Color{
    return when(baseStat){
        in 0..59 -> WorstStat
        in 60..79 -> BadStat
        in 80 ..89 -> RegularStat
        in 90..99 -> NormalStat
        in 100..109 -> GoodStat
        in 110..119 -> NiceStat
        in 120..129 -> SuperStat
        in 130..139 -> HiperStat
        in 140..149 -> MegaStat
        in 150..160 -> MaxiStat
        else -> MasterStat
    }
}
