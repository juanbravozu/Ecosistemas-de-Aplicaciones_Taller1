# Ecosistemas de Aplicaciones_Taller1

## Android Studio
### MainActivity
Extiende de AndroidAppActivity.

**Variables:**
- -ip - EditText: Introducir dirección ip para conectarse al servidor.
- -nombre - EditText: Introducir el nombre del usuario o personaje.
- -continuar - Button: Validar la información y conectarse al servidor.
- -ref - Comunicacion: Permite conectarse al servidor.

**Métodos:**
- +OnCreate(Bundle): Método básico para inicializar variables.

***
### Control
Extiende de AndroidAppActivity e implementa Observer.

**Variables:** 
- -atacar - Button: Botón para hacer que el personaje ataque.
- -cubrir - Button: Botón para hacer que el personaje se cubra con el escudo.
- -analogo - JoystickView: [Análogo](https://github.com/controlwear/virtual-joystick-android) para controlar el movimiento del personaje. 
- -ref - Comunicacion: Permite la transferencia de datos entre AndroidStudio y el servidor en Eclipse.

**Métodos:**
- +OnCreate(Bundle): Método para inicializar variables y detectar movimiento del análogo.
- +update(Observable, Object): Método para recibir los cambios en la variable observada(ref).

***
### Comunicacion
Extiende de Observable e implementa Runnable.

**Variables:**
- -entrada - DataInputStream: Recibe datos enviados desde el servidor.
- -salida - DataOutputStream: Envía datos al servidor.
- -conectado - boolean: Indica si se ha iniciado la conexión con el servidor.
- -ref - Comunicacion: Instancia única de esta clase.

**Métodos:**
- -Comunicacion(): Constructor, inicializa algunas variables.
- +getRef(): Retorna esta instancia de Comunicacion, inicia la comunicación si no se ha hecho ya.
- +getRef(String): Retorna esta instancia de Comunicacion, iniciando la comunicación con la ip recibida a través del String si no se ha hecho ya.
- +enviar(String): Envía información al servidor.
- -recibir(): Recibe información del servidor e informa a los observadores.
- +run(): Se encarga de recibir y envíar información constántemente.

***
## Eclipse/Servidor
### Main
Clase ejecutable, extiende de PApplet.

**Variables:**
- -log - Logica: Clase Lógica, en la que se realizan la mayoría de procesos.

**Métodos:**
- +settings(): Establece el tamaño del lienzo.
- +setup(): Inicializa las variables.
- +draw(): Pinta los elementos en el lienzo.

***
### Logica
Implementa Observer.

**Variables:**
- -app - PApplet
- -pantalla - int
- -tiempo - int
- -puntos - int
- -musica - SoundFile
- -fondos - PImage[]
- -nombre - String: Nombre del jugador.
- -cs - ControlServidor: Inicia comunicación con el cliente.
- -jugador - Jugador
- -enemigos - ArrayList<Enemigo>

**Métodos:**
- +Logica(PApplet)
- +pintar()
- +update(Observable, Object): Recibe la información enviada por la variable observada(cs).
- +pasarNivel()

***
### ControlServidor
Extiende de Observable e implementa Runnable.

**Variables:**
- -entrada - DataInputStream: Recibe datos enviados desde el servidor.
- -salida - DataOutputStream: Envía datos al servidor.
- -conectado - boolean: Indica si se ha iniciado la conexión con el servidor.
- -ref - ControlServidor: Instancia única de esta clase.
- -ip - InetAddress: Muestra la ip local para que el cliente pueda conectarse.

**Métodos:**
- -ControlServidor(): Constructor, inicializa algunas variables.
- +getRef(): Retorna esta instancia de ControlServidor, inicia la comunicación si no se ha hecho ya.
- +getRef(String): Retorna esta instancia de ControlServidor, iniciando la comunicación con la ip recibida a través del String si no se ha hecho ya.
- +enviar(String): Envía información al servidor.
- -recibir(): Recibe información del servidor e informa a los observadores.
- +run(): Se encarga de recibir y envíar información constántemente.

***
### Jugador
Extiende de Thread.

**Variables:**
- -app - PApplet
- -pos - PVector
- -quietoDer - PImage[]
- -quietoIzq - PImage[]
- -andarDer - PImage[]
- -andarIzq - PImage[]
- -atkDer - PImage[]
- -atkIzq - PImage[]
- -guardDer - PImage[]
- -guardIzq - PImage[]
- -vida - int
- -tam - int

**Métodos:**
- +Jugador(PApplet)
- +pintar(): Muestra las imágenes correspondientes a las acciones del personaje en la posición indicada por pos.
- +mover()
- +atacar()
- +cubrir()
- +run()

***
### Enemigo
Clase abstracta. Extiende de Thread.

**Variables:**
- #log - Logica: Instancia de la clase Logica para establecer un comportamiento teniendo en cuenta los demás elementos en el entorno.
- #app - PApplet
- #pos - PVector
- #img - PImage[]
- #andar - PImage[]
- #atacar - PImage[]
- #vida - int
- #timerAtk - int

**Métodos**
- +Enemigo(PApplet, Logica)
- +pintar()
- +atacar(): Método abstracto
- +mover(): Método abstracto

***
### CortoAlcance
Extiende de Enemigo.

**Hereda todas sus variables de Enemigo**

**Métodos:**
- +CortoAlcance(PApplet, Logica)
- +mover(): Intentará acercarse al jugador.
- +atacar(): Si está cerca, atacará al jugador.

***
### LargoAlcance
Extiende de Enemigo.

**Hereda todas sus variables de Enemigo**

**Métodos:**
- +LargoAlcance(PApplet, Logica)
- +mover(): Caminará sin un rumbo establecido por el escenario hasta que el jugador esté dentro de su alcance, entonces se quedará quieto para atacar.
- +atacar(): Si el jugador está dentro de cierto rango, lanzará proyectiles hacia él.
