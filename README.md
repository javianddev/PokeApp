POKEAPP es una aplicación realizada enteramente con Jetpack Compose y Kotlin.

Se han utilizado, entre otras, las siguiente librerías:
- Jetpack Compose
- Compose Navigation
- Material 3
- Room
- Retrofit2
- Coil
- Dagger & Hilt
- Paging3
- Junit
- Mockito

El objetivo de la aplicación ha sido el manejo de datos recuperados de una API remota como pokeapi y el manejo de datos desde local con la ayuda de Room. También se ha hecho una iniciación al testing, probándose la UI cuyo enfoque ha sido hacia la navegación y el correcto funcionamiento de la BBDD.

La aplicación cuenta con:
Perfil individual donde editar nuestros datos personales (nombre, ciudad y fecha de nacimiento). También podremos elegir nuestros 6 pokémon favoritos. Estos datos son persistentes gracias a Room.
Acceso a una pokédex y a datos básicos de cada pokémon. Se accede a una API a través de Retrofit. La carga de datos es dinámica gracias a Paging3
Trivial de preguntas pokémon. Podremos ganar medallas e ir desbloqueando nuevas regiones. Persistencia de datos con Room.

Algunas pantallas de la aplicación:

Acerca de PokeApp dentro de la aplicación (uso de Modal):


https://github.com/javianddev/PokeApp/assets/142154025/e11110e6-f85f-4e31-9a28-64a38bf3ba73

Trivial completamente funcional con contador de tiempo.


https://github.com/javianddev/PokeApp/assets/142154025/b8328790-78c6-4195-8ded-e631fc0c507b



El contador de tiempo es totalmente funcional. ¡Responde rápido!


https://github.com/javianddev/PokeApp/assets/142154025/973d1005-18be-4dc8-a07c-bea3b0ff18cb






