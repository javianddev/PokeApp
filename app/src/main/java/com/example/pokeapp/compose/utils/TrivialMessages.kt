package com.example.pokeapp.compose.utils

object TrivialMessages {
    val initialMessages : List<String> = listOf(
        "Bienvenido entrenador. Veo que estás preparado para un nuevo desafío.",
        "Vamos a ver qué tal se te da. Si respondes correctamente a todas las preguntas conseguirás las medallas de la región. ¡Suerte!",
    )

    val failMessages : List<String> = listOf(
        "Parece que aún no estás del todo preparado.",
        "Deberías de seguir aprendiendo en la Pokescuela, nos vemos en la próxima"
    )

    val winnerMessages: List<String> = listOf(
        "¡Felicidades!, has conseguido las medallas de la región",
        "A partir de ahora las podrás ver en tu perfil de entrenador",
        "Ahora podrás competir en la siguiente región, ¡suerte!"
    )
}
