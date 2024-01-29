package com.example.pokeapp.compose.trainer


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokeapp.R
import com.example.pokeapp.compose.navigation.AppScreens
import com.example.pokeapp.compose.utils.ErrorScreen
import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.data.models.Region
import com.example.pokeapp.data.models.Trainer
import com.example.pokeapp.remotedata.model.PokemonEntry
import com.example.pokeapp.utils.toFormattedString
import com.example.pokeapp.viewmodels.PokedexUiState
import com.example.pokeapp.viewmodels.SearchBarStateUi
import com.example.pokeapp.viewmodels.TrainerViewModel
import java.util.Locale

@Composable
fun TrainerScreen(modifier: Modifier = Modifier, viewModel: TrainerViewModel = hiltViewModel(), navController: NavController){

    val trainerState by viewModel.uiState.collectAsState()
    val modalSheet by viewModel.modalSheet
    val regionMedal = viewModel.regionMedal
    val searchBarState by viewModel.searchBarState
    val pokedexUiState = viewModel.pokedexUiState


    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TrainerInfo(trainerState.trainer, { viewModel.getTrainerData() },navController)
        }

        item {
            Text(
                text = "Medallas",
                style = MaterialTheme.typography.titleLarge
            )
            MedalsCard(regionMedal, /*trainerState.medals,*/ modifier = modifier)
        }

        item {
            Text(
                text = "Equipo Pokémon",
                style = MaterialTheme.typography.titleLarge
            )
            TeamCard(trainerState.pokemonTeam, modifier = modifier, openModalSheet = { viewModel.openModalSheet(it) })
        }
    }
    if (modalSheet){
        PokeTeamModalSheet({ viewModel.closeModalSheet() }, { viewModel.getPokedex() },
            {viewModel.setQuery(it)}, {viewModel.setActive(it)}, {viewModel.setPokemonTeam(it)}, { viewModel.getPokemonTeam() },searchBarState, pokedexUiState)
    }
}

@Composable
fun TrainerInfo(trainer: Trainer, getTrainerData: () -> Unit,navController: NavController){

    LaunchedEffect(Unit){
        getTrainerData()
    }

    Text(
        text = stringResource(id = R.string.trainer_info),
        style = MaterialTheme.typography.titleLarge
    )
    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
        ) {
            Image(
                painter = painterResource(id = R.drawable.pokemon_trainer),
                contentDescription = stringResource(id = R.string.default_trainer_image),
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(150.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Nombre de entrenador: ${trainer.name}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Lugar de nacimiento: ${trainer.birthplace}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Fecha de nacimiento: ${trainer.birthdate.toFormattedString()}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { navController.navigate(AppScreens.EditTrainer.route) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.testTag("edit_trainer")
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_trainer),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
fun MedalsCard(regionMedal: List<Pair<Region, List<Int>>>, /*medals: List<Medal>,*/ modifier: Modifier = Modifier){

    val imageModifier = Modifier
        .padding(end = dimensionResource(id = R.dimen.padding_small))
        .size(dimensionResource(id = R.dimen.medal_image))

    Card(
        elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
    ){
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.vertical_space)), modifier = Modifier.padding(
            dimensionResource(id = R.dimen.padding_small))){
            regionMedal.forEach{
                Text(
                    text = "${stringResource(id = R.string.region_medal)} ${it.first.name}",
                    style = MaterialTheme.typography.titleMedium
                )

                val filter =  if (!it.first.medalAchieved){
                    ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) })
                }else{
                    null
                }
                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                    it.second.forEach { medal ->
                        Image(
                            painter = painterResource(id = medal),
                            contentDescription = stringResource(id = R.string.medal_achieved),
                            colorFilter = filter,
                            modifier = imageModifier
                        )
                    }
                    /*items(medals.filter{ it.id == region.id}){medal ->
                        Image( /*TODO Si averigüo la mejor manera de enlazar los drawables con la BBDD*/
                            painter = painterResource(id = medal.image),
                            contentDescription = medal.medalName,
                            modifier = imageModifier
                        )
                    }*/
                }
            }
        }
    }

}

@Composable
fun TeamCard(pokemonTeam: List<Pokemon>, openModalSheet: (Int) -> Unit,modifier: Modifier = Modifier) {

    val positions = listOf(1,2,3,4,5,6)

    Card(elevation = CardDefaults.cardElevation(dimensionResource(id = R.dimen.default_card_elevation)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))){
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ){
            for ((index, i) in positions.withIndex()){
                val datoIndex = pokemonTeam.indexOfFirst { it.id == i }
                val pokemon = if (datoIndex != -1) pokemonTeam[datoIndex] else null
                if (pokemon != null){
                    AsyncImage(
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(pokemon.imageUrl)
                            .crossfade(true)
                            .build(),
                        error = painterResource(R.drawable.ic_broken_image),
                        placeholder = painterResource(R.drawable.loading_img),
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(dimensionResource(id = R.dimen.pokemon_team_image))
                            .clickable { openModalSheet(index + 1) }
                    )
                } else {
                    IconButton(onClick = { openModalSheet(index + 1) }, modifier = Modifier.padding(
                        dimensionResource(id = R.dimen.padding_small))) {
                        Icon(
                            imageVector = Icons.Filled.AddCircle,
                            contentDescription = stringResource(id = R.string.add_pokemon),
                            tint = Color.Gray,
                            modifier = Modifier.size(dimensionResource(id = R.dimen.icon_team))
                        )
                    }
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeTeamModalSheet(closeModalSheet: () -> Unit, getPokedex: () -> Unit, setQuery: (String) -> Unit, setActive: (Boolean) -> Unit,
                       setPokemonTeam: (PokemonEntry) -> Unit, getPokemonTeam: () -> Unit, searchBarState: SearchBarStateUi, pokedexUiState: PokedexUiState){

    val modalBottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit){
        getPokedex()
    }

    ModalBottomSheet(
        onDismissRequest = { closeModalSheet() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        containerColor = MaterialTheme.colorScheme.background
    ) {//Al separar de aqui la SearchBar evitamos la recomposición de esta función y no se lanza getPokedex muchas veces
        PokedexSearchBar(searchBarState, pokedexUiState, {setQuery(it)}, {setActive(it)}, {setPokemonTeam(it)}, { closeModalSheet() }, { getPokemonTeam() })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexSearchBar(searchBarState: SearchBarStateUi, pokedexUiState: PokedexUiState,
                     setQuery: (String) -> Unit, setActive: (Boolean) -> Unit, setPokemonTeam: (PokemonEntry) -> Unit, closeModalSheet: () -> Unit, getPokemonTeam: () -> Unit){
    SearchBar(
        query = searchBarState.query,
        onQueryChange = { setQuery(it) },
        onSearch = { setActive(false) },
        active = searchBarState.active,
        onActiveChange = { setActive(it) },
        placeholder = { Text(text = stringResource(id = R.string.search_pokemon)) },
        trailingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.background),
        modifier = Modifier.padding( //En top no quiero nada que lleva de por sí el ModalSheet y va a quedar feo
            bottom = dimensionResource(id = R.dimen.padding_large),
            start = dimensionResource(id = R.dimen.padding_medium),
            end = dimensionResource(id = R.dimen.padding_medium)
        )
    ){
        when (pokedexUiState){
            is PokedexUiState.Success -> PokedexScreen(pokedexUiState.pokemons, searchBarState, {setPokemonTeam(it)}, { closeModalSheet() }, { getPokemonTeam() })
            is PokedexUiState.Error -> ErrorScreen(Modifier.fillMaxSize())
            is PokedexUiState.Loading -> Unit /*TODO Poner algo, no es tan importante*/
        }
    }
}

@Composable
fun PokedexScreen(pokemons: List<PokemonEntry>, searchBarState: SearchBarStateUi, setPokemonTeam: (PokemonEntry) -> Unit, closeModalSheet: () -> Unit, getPokemonTeam: () -> Unit){
    if (searchBarState.query.isNotEmpty()){
        val filteredPokemons = pokemons.filter{ it.name.contains(searchBarState.query)}
        filteredPokemons.forEach{
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.horizontal_space)),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    setPokemonTeam(it)
                    closeModalSheet()
                    getPokemonTeam()
                }
            ){
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(it.imageUrl)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.ic_broken_image),
                    placeholder = painterResource(R.drawable.loading_img),
                    contentDescription = it.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(36.dp)
                )
                Text(
                    text = it.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}