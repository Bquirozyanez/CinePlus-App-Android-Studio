## Nota importante profesor: 
Hola profe, este README fue agregado más tarde de la hora de entrega. Hice el README correcto a las 23:58 aproximadamente y este es uno actualizado 
(cualquier cosa, tengo un video donde grabé el README de las 23:58 por cualquier cosa). No quería que el README me bajara mucho puntaje porque, si bien lo realicé,
fue un README hecho un poco a la rápida y que siento que no le hace honor al trabajo. Espero que pueda dejármelo pasar por esta vez y ser piadoso jajaja.
Este README solo quise que quedara más bonito y “mejor” explicado. Espero pueda comprender la situación y para ser honesto dejo este mensaje al principio del READ.ME.

# Caso elegido: Proyecto CinePlus



## 1. Caso elegido y alcance

Alcance EP3:
En esta entrega trabajamos principalmente en el diseño de las pantallas principales, siendo estas:
- Login (ProfileScreen)
- Registro (RegisterScreen)
- Resumen (ResumenScreen)
- Cartelera (HomeScreen)

Trabajamos la navegación entre pantallas partiendo desde el Login. El registro de usuarios con validaciones y conexión a un backend externo (Xano, que se encontraba en el Anexo A).
Inicio de sesión conectado a este backend. La pantalla de Resumen muestra los datos ingresados antes de ir a la cartelera. Una cartelera con películas con la integración de un modo oscuro y los siguientes recursos nativos:
- Cámara con función de QR para las películas y/o entradas.
- Mapa (que, si bien se estuvo trabajando en la implementación de este recurso nativo, no se alcanzó a concretar).



## 2. Requisitos y ejecución

Tener Android Studio con Kotlin, Jetpack Compose y disponer de conexión a internet. 

Instalación:
Abrir el proyecto en Android Studio y clonar el repositorio. Luego presionar el botón de play y listo!



## 3. Arquitectura y flujo

- ui/screen: Contiene todas las pantallas visibles para el usuario.
- navigation: Se encarga de movernos entre pantallas.
- data/remote: Conecta la app con la API de Xano.
- repository: Une los datos con la lógica de la app.
- viewmodel: Controla lo que pasa en cada pantalla y guarda su estado.
- data: Guarda cosas locales, como la preferencia del modo oscuro.


app/src/main/java/com/example/cineplus/
│
├── data/ ← CAPA DE DATOS
│ ├── remote/ 
│ │ ├── ApiService.kt 
│ │ ├── RetrofitClient.kt
│ │ └── dto/ 
│ │ ├── LoginRequest.kt
│ │ ├── LoginResponse.kt
│ │ └── UserDto.kt
│ └── DarkModeDataStore.kt
│
├── repository/ 
│ └── AuthRepository.kt 
│
├── ui/ 
│ ├── navigation/ 
│ │ ├── AppNavigation.kt
│ │ └── Screen.kt
│ └── screens/ 
│ ├── LoginScreen.kt
│ ├── RegisterScreen.kt
│ ├── ResumenScreen.kt
│ ├── HomeScreen.kt
│ └── ProfileScreen.kt
│
├── viewmodel/ 
│ ├── UsuarioViewModel.kt
│ ├── RegisterViewModel.kt 
│ └── DarkModeViewModel.kt
│
└── model/ 



## 4. Funcionalidades

Registro e inicio de sesión:
- El usuario puede crear una cuenta con su nombre, correo y contraseña (mínimo 6 caracteres).
- Luego puede iniciar sesión con esos mismos datos.
- Ambas acciones se conectan a la API que valida la información.

Pantalla de resumen:
- Después del registro, se muestra un resumen con los datos ingresados antes de pasar a la cartelera.
- Sirve como una confirmación de que el registro fue exitoso.

Cartelera (Home):
- Muestra una lista de películas con su imagen, título y duración.

Modo oscuro:
- El usuario puede activar o desactivar el modo oscuro.
- La app recuerda esta preferencia gracias al DataStore.

Recursos nativos: cámara y mapa:
- Desde una de las pantallas, la app permite acceder a la cámara del dispositivo para el uso de Scanner QR.
- El recurso nativo de mapa no alcanzó a ser implementado.

Conexión con API externa:
- Se usó Retrofit para comunicarse con la API de Xano, que maneja el registro e inicio de sesión.
- Si la respuesta de la API es correcta, la app navega a la siguiente pantalla; si no, muestra un mensaje de error. 



## 5. Endpoints

La app se conecta con un backend creado en Xano, que maneja todo lo relacionado con los usuarios (registro, login y obtención de datos). Para esto se usa Retrofit, una librería que permite hacer solicitudes HTTP desde Android Studio.

Los principales endpoints usados en el proyecto son los siguientes:

| Función | Método | Endpoint | Descripción |
|----------|---------|-----------|--------------|
| Registro de usuario | **POST** | `https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/auth/signup` | Envía los datos del usuario para crear una cuenta nueva. 
| Inicio de sesión | **POST** | `https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/auth/login` | Valida los datos y devuelve un token si el login es correcto. 
| Datos del usuario | **GET** | `https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW/auth/me` | Obtiene la información del usuario que se loguea. 

En caso de error (por ejemplo, una contraseña muy corta o datos incorrectos), la API devuelve un código HTTP 400, que la app muestra en pantalla para que el usuario pueda corregirlo.



## 6. User flows

El flujo de usuario de esta app consiste en:
1) El usuario ingresa a la app.
2) Pone su usuario y contraseña; si es primera vez que la usa, la app le dará error y tendrá que registrarse.
3) La app lo lleva a la Screen de registro, donde indica su usuario, correo, contraseña y dirección.
4) Se le muestra un resumen con sus credenciales y finalmente puede acceder al catálogo de películas en el HomeScreen.
5) El usuario, ya en el HomeScreen (catálogo de películas), puede ver qué películas están disponibles junto con su duración.












