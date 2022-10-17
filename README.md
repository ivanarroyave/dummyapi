# Automatización de servicios - Dummy API

_A continuación se explicara la composición y consideraciones necesarias para interactuar por medio de automatizaciones con Dimmy API._

## Comenzando 🚀

_En este proyecto se pondrá en práctica conocimientos para automatizaciones de pruebas aplicadas a servicios y además, la ejecución de las pruebas por medio de Jenkins, el cual estará en un contenedor de Docker._

_Este proyecto ilustra cómo trabajar con el patrón de automatización Screenplay bajo el framework de automatización llamado Serenity BDD: https://serenity-bdd.info/._



### Pre-requisitos 📋

_Todo el proceso de ejecución de pruebas se hará localmente. Así que ten presente los siguientes pre-requisitos para tener una buena experiencia de replicación. Además, si no se indica una versión para alguna herramienta pues deberás asegurar la versión más reciente._
```
Java 8+
InteliJ IDEA (plugins: Gherkin; Cucumber for Java; Substeps Intellij plugin, Maven)
Maven 3.8.6
Git
Doker
```

### Adecuación local del proyecto de automatización 🔧

_Antes que nada, debes clonar el repositorio en tu equipo. Y si, eso es todo ya que se supone que dispones de los pre-requisitos._


```
git clone https://gitlab.com/ID2A/dummyapi.git
```


_Una vez hecho, verás la carpeta "dummyapi" la cual se generó por la clonación del repositorio; a su vez contiene, el proyecto de automatización está dentro de otra carpeta también llamada "dummyapi"._

## Ejecutando las pruebas ⚙️

_Primero deberás abrir el proyecto de automatización:_
* Haga clic en Archivo > Abrir.
* Navegue hasta el archivo pom.xml del proyecto.
* Haga clic en Aceptar.

### Analice las pruebas 🔩

_Las pruebas en este proyecto se basan en las funcionaliadaes descritas en https://dummyapi.io/docs/user y https://dummyapi.io/docs/errors._
_Se han automatizado un todal de 11 escenarios de pruebas entre rutas ideales y alternas. Las descripciones de los Features y sus respectivos Scenarios las puede encontrar en la ruta: "src/test/resources/features/user"._

_Por cada Feature existe una clase runner en la ruta "src/test/java/io/dummyapi/runners/"._
```
* CreateUserRunner.
* DeleteUserRunner.
* SearchUserRunner
* UpdateUserRunner.
```

_Existen dos runner adicionales que representan una Suite de runners. Uno con Cucumber con Serenity y otro con Junit._
```
* AnExampleOfGeneralExecutorOfTest. Forma de Suite con CucumberWithSerenity.
* DeleteUserRunner. Forma de Suite con Junit.
```

### Ejecute localmente las pruebas y genere un reporte de pruebas ⌨️

_Para ejecutar las pruebas deberá hacerlo desde una consola o terminal. Navegue hasta donde se encuentre el archivo pom.xml. Ahora, desde la consola:_

_Primero: limpie el proyecto de archivos temporales._
```
mvn clean
```

_Segundo: ejecute una o varias pruebas. Para la ejecución de pruebas el comando es diverso. Veamos un ejemplo para ejecutar un runner específico; será el Suite con CucumberWithSerenity. La idea de este formato es ejecutar todos los escenarios deseados desde una Suite._
```
mvn test -Dtest=AnExampleOfGeneralExecutorOfTest
```

_Si desea ejecutar TODAS las pruebas use el siguiente comando:_
```
mvn verify
```

_Para generar el reporte de pruebas. El reporte se genera normalmente en la ruta "./dummyapi/target/site/serenity/index.html". Para verlo, debe abrir el archivo "index.html"._
```
mvn serenity:aggregate
```

_Tambien se puede ejecutar todo desde una sola línea._
```
mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate
```

### Ejecute localmente las pruebas y genere un reporte de pruebas desde un contenedor de Docker con una imagen de Jenkins⌨️
_De antemano ten presente que nos va a interesar guardar las configuraciones de Jenkins en un volumen de Docker externo al contenedor. Una vez que se configure Jenkins no queremos repetir el proceso una y otras vez si por alguna razón perdemos el contenedor. Mejor tenerlo a la mando para reusar las configuraciones de Jenkins (plugins instalados, pipelines configurados, usuarios, etc.) en un nuevo contenedor._

#### Ejecución con Docker compose ☕



## Autores ✒️

* **Iván Darío Arroyave Arboleda** - *Ingeniero de sistemas de información - Especializado en el aseguramiento de la calidad del software* - [ID2A](https://gitlab.com/ID2A)

## Expresiones de Gratitud 🎁

* Comenta a otros sobre este proyecto 📢
* Invita una cerveza 🍺 o un café ☕ a alguien del equipo. 
* Da las gracias públicamente 🤓.

* etc.



---
⌨️ con ❤️ por [Iván Darío Arroyave Arboleda](https://gitlab.com/ID2A) 😊