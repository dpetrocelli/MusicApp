# MusicAPP

## 1 - Problemática actual

En base a la información recopilada a partir de las tareas de relevamiento del estado de situación actual a través de encuestas y entrevistas, encontramos que los principales inconvenientes son: la falta de conocimiento y vinculación entre artistas y respectivas bandas y la  falta de información acerca de salas de ensayo, tiendas, etc, por lo que decidimos crear MiusicApp.

## 2 - Objetivo y Descripción de la solución

Se propone y desarrolla un sistema web que permite solventar la falencia antes descrita y proponer una plataforma que tome ventajas de lo que hoy es una problemática en la ciudad de Buenos Aires.  
Dicha plataforma permitirá, por un lado, que músicos profesionales y amateurs puedan interactuar entre sí y buscar rápidamente a otros músicos, para crear una banda musical nueva o sumar, a una ya formada, uno o varios integrantes. La plataforma permitirá a los usuarios buscar a otro usuario o banda, calificarlos, invitar a un músico a sumarse a la misma o un músico puede postularse a una banda.  
Por otro lado, permite a los distintos sectores relacionados con el mundo de artistas y músicos vincularse en la misma plataforma para publicitar sus servicios y ofrecer promociones.  De esta manera, la plataforma compuesta por estos dos subsistemas, permite vincular y relacionar a las partes involucradas

### 2.1 - Subsistema de Gestión de Usuarios
La aplicación se desarrollará en español y sólo funciona para usuarios registrados. Por lo tanto, cada artista interesado o entidad externa tendrá la posibilidad de generar un usuario en el sistema y a partir de allí acceder a la funcionalidad que le corresponda (según el rol asignado).

### 2.2 - Subsistema Red de Artistas y Bandas
Cada músico, una vez registrado, poseerá un usuario y contraseña para acceder a la aplicación.  A partir de allí, el artista podrá:
* Cargar su información biográfica, incluyendo material audiovisual (linkeado a la plataforma que corresponda) que le competa (fotos, videos, etc.) 
* Asignar los instrumentos que toca
* Asignar estilo musical 
* Zona geográfica de residencia. 

El usuario podrá modificar su perfil cuando lo desee, agregando, modificando y quitando el material que considere necesario a efecto que puedan conocerlo. Los videos podrán ser vistos desde nuestra aplicación pero los mismos deberán encontrarse  cargados en la plataforma de Youtube, se visualizará un video con la opción de poder ver una lista de vídeos con un link a dicha plataforma, las fotos podrán ser subidas a nuestra plataforma y también cada músico podrá publicar un link a Spotify con su lista de temas, link a perfil de Instagram o Facebook.  
A su vez, para garantizar la reputación, compromiso y profesionalidad del artista o de una banda, el sistema permite a los músicos y bandas calificarse entre sí con un valor numérico (entre 1 y 5),  y se puede visualizar públicamente el promedio total de ambas partes.
Por su parte, los perfiles de cada banda son administrados por los usuarios participantes de la misma. El perfil de cada banda, al igual que el de un artista, incluye una breve descripción de la misma, género musical y si la banda lo desea y dispone, vínculo a material en Youtube,  Spotify o página oficial de Facebook, Instagram, sumado a imágenes y comentarios que puede subir a la plataforma. Con respecto a los videos, al igual que en el caso de los músicos un video se ve en nuestra aplicación y se puede incluir un link a Youtube con una lista de videos 
Cada músico logueado al sistema puede realizar búsquedas de artistas y/o bandas utilizando filtros por instrumento que toca, estilo musical o  ubicación geográfica.  Las bandas pueden contactarse con algún músico en caso de que desee incorporarlo al grupo y los músicos pueden postularse a las bandas si desean formar parte de ella. Ambos procesos requieren de un proceso de moderación por la respectiva parte (banda o músico) para permitir el ingreso a la banda o para aceptar la vinculación a una banda.

### 2.3 - Subsistema de Modelo de Negocio (Cuenta Corriente)
Para que el sistema diseñado tenga la capacidad de proporcionar ganancias, beneficios económicos y sea sustentable en el tiempo, se definió un modelo de negocio que permita que la plataforma pueda mantener sus costos de mantenimiento básicos (recursos IaaS Cloud en Amazon, registro de nombre DNS, etc), costos de crecimiento y escalabilidad (recursos elásticos en la nube, correcciones y nuevas funcionalidades) y obtención de ganancias, se definieron dos aristas principales. 
* a) **Suscripciones**.  A través de abonos mensuales que deben abonar las entidades relacionadas con la plataforma (salas de ensayo, locales de artículos musicales, etc) que serán publicitadas en el sistema en el apartado de “Salas de ensayo” y “Auspiciantes”.  
* b) **Promociones**. Cada entidad externa que tenga abono mensual puede generar promociones (set de cupones) de descuento o beneficios para los artistas registrados. 

Para poder manejar las transacciones definidas (suscripciones y promociones), nuestro sistema dispondrá de una cuenta en un sistema de pago online (tercerizado) donde volcará sus ingresos y egresos de dinero. A su vez, será requisito que estas entidades externas dispongan una cuenta en dicha plataforma tercerizada que se asocie al usuario que se generó al registrarse en el sistema para vincular las transacciones entre las dos partes.  De esa manera, el sistema almacenará todos los movimientos económicos que se generen entre la plataforma y las entidades externas relacionadas en el sistema tercerizado, registrando el tipo de operación (ingreso/egreso de dinero) en la cuenta corriente, emisor/destino de la operación, fecha de operación y monto. Para los administradores del sistema, la aplicación posee una usuario especial que permite el acceso a la “sección de cuenta corriente” para poder visualizar las el estado de la cuenta y ganancias obtenidas. 

### Plataforma de pagos tercerizada
Para esta actividad se integró la plataforma de **MercadoPago** con objeto de poder integrar transacciones reales al manejo de l sistema. 
“La plataforma de pago de Mercado Libre resuelve la transacción atendiendo las necesidades de los dos eslabones de la cadena: el que compra y el que vende” (Mercado Libre, 2019)
Mercado pago posee una API. “Es una Interfaz de Programación de Aplicaciones (Application Programming Interface) que te permite intercambiar información con nuestra plataforma.”
Mercado Pago cuenta con APIs para poder recibir pagos de forma segura en tu sitio web, aplicación móvil, o donde desees
Cuentas con dos pares de claves para conectarte con la API, uno para un entorno de pruebas y otro productivo
La credencial pública, o public key, es la utilizada para acceder a todos los recursos que necesitará tu frontend para recolectar los datos de tarjeta de crédito, y tokenizar.
La credencial privada, o access token, se utiliza para todas las otras llamadas a las APIs, como procesar un pago, realizar reembolsos, almacenar tarjetas y más. Las claves privadas deben ser mantenidas confidencialmente en tus servidores de backend y nunca deben ser publicadas.

Un MarketPlace es un sitio o aplicación que permite a vendedores y compradores relacionarse para efectuar una transacción comercial. El propietario del Marketplace proporciona espacio a los vendedores para mostrar sus bienes o servicios, y se encarga de gestionar todos los aspectos de la transacción. Por ejemplo, Mercado Libre es un Marketplace.

Mercado Pago te permite realizar cobros a nombre de los vendedores de tu plataforma y opcionalmente cobrar una comisión por la transacción.

Para ello el manejo de la API de MercadoPago y , se definen las siguientes acciones:

**a) Manejo de pago de suscripciones**
Mensualmente se registran los pagos de la entidad externa en la cuenta corriente de la aplicación.  Superado los 3 días hábiles de la fecha de vencimiento de la suscripción temporal sin haber recibido el pago, la plataforma quitará la disponibilidad de la entidad de la respectiva sección e inhabilitará el acceso a las promociones publicadas por la entidad en cuestión.  Todos los registros almacenados serán de tipo ingreso económico (suma en la cuenta corriente) 

**b) Manejo de Promociones** 
El sistema provee la funcionalidad de generar promociones por parte de los “Lugares Externos”.  Es decir, los Locales de Productos Musicales, Salas de ensayos, etc, una vez logueados con su usuario registrado en el sistema generar promociones para aplicar en su local/servicio.  Para ello, podrán definir un cupón “giftcard” por un valor económico y una escala de ahorro que recibirá el cliente.  Dicha promoción tendrá una vigencia desde/hasta, una cantidad de cupones de la promoción (1 a N).  Una vez finalizado el proceso, será publicado en sitio de MiusicApp en el apartado de promociones, donde el usuario final accederá a los beneficios realizando el pago a través de una cuenta en un sistema de pagos tercerizado (Mercado Pagos).
Cabe destacar que el porcentaje de ganancia obtenido por el sistema MiusicApp es definido y mantenido dentro del sistema por parte de los administradores a partir de la definición del parámetro “Porcentaje de ganancia” y las entidades externas no pueden modificar dicha proporción.
Por su parte, los Artistas registrados, para acceder a un cupón deberán realizar la compra a través de la plataforma utilizando el mismo medio de pago que se describió anteriormente.  Una vez que el cliente realiza el pago, se derivan los siguientes procesos:
1) Recepción de pago por parte del usuario por el valor del cupón de ahorro
2) El sistema calcula el porcentaje de ganancia en base al valor del cupón de ahorro
3) Luego, deriva transferencia del dinero al ente emisor del cupón por la diferencia entre el valor del cupón y las ganancias propias del sistema, manteniendo solo en la cuenta corriente local el porcentaje correspondiente a la ganancia
4) Usuario obtiene el cupón de beneficio (en ese momento o a demanda)

### 2.4 - Subsistema de Gestión de Promociones
Relacionado con el apartado anterior, las entidades externas podrán definir promociones económicas y cupones de canje asociados. Para ello, el sistema le permitirá, a través de la GUI, definir:
* Monto de la promoción (ej. 1000$)
* Valor del ahorro obtenido por el usuario (ej, $100)
* Cantidad de cupones por promoción (ej, 20)
* Fecha de vigencia de la promoción (fecha desde, fecha hasta)

Una vez generada la promoción, será publicada en el área de “Promociones” del sitio de MiusicApp.  A partir de ese momento, el sistema se encarga de todo el proceso vinculado con el cobro y entrega al usuario al final.  Una vez que el usuario final accede al cupón de descuento, el mismo podrá ser canjeado en el local emisor del mismo

### 2.5 Subsistema de Información de Salas de Música
Otra de las secciones de “MiusicApp” son las salas de ensayo donde los músicos  y bandas pueden realizar búsquedas filtrando por ubicación. Cada sala de ensayo posee una breve descripción de la misma, una foto de la sala, la ubicación y el contacto.  En esta instancia, no se permitirá reservar lugares físicos para las reuniones, cobrar o pagar por servicios musicales, shows o canciones, se podrán incorporar dichas funcionalidades en futuros releases.


## 3 - Tecnologías utilizadas  🛠️

_Para desarrollar el proyecto se utilizaron diversas tecnologías de desarrollo, implementación y automatización_

* [Dropwizard](http://www.dropwizard.io/1.0.2/docs/) - El framework web usado
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [ROME](https://rometools.github.io/rome/) - Usado para generar RSS


⌨️ con ❤️ por [David Petrocelli](https://github.com/dpetrocelli) 😊
