package com.example.pokeapp.daoTest

import com.example.pokeapp.data.models.Pokemon
import com.example.pokeapp.data.models.Question
import com.example.pokeapp.data.models.Region
import com.example.pokeapp.data.models.Solution
import com.example.pokeapp.data.models.Trainer
import java.time.LocalDate

/********************** POKEMON ******************************/
val pokemon1 = Pokemon(1, 45, "seel", "urlImagenFicticia")
val pokemon2 = Pokemon(1, 47, "venonat", "urlImagenFicticia")
val pokemon3 = Pokemon(3, 1, "bulbasaur", "urlImagenFicticia")
/********************** SOLUTION ******************************/
val solution1 = Solution(1, "Koga", false, 1)
val solution2 = Solution(2, "Lorelei", true, 1)
/********************** QUESTION ******************************/
val question1 = Question(1, "¿Quién es el primer Alto Mando de Kanto?", 1)
/********************** REGION ******************************/
val region1 = Region(1, "Kanto", false)
val region2 = Region(2, "Hoenn", false)
val region3 = Region(1, "Kanto", true)
/********************** TRAINER ******************************/
val trainer1 = Trainer(1, "Javier", LocalDate.of(1997,3,1), "Pueblo Paleta")
val trainer2 = Trainer(1, "Javi", LocalDate.of(1997,3,1), "Sevilla")