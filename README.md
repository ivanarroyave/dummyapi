# Automatizaci¨®n de servicios - Dummy API

_A continuaci¨®n se explicara la composici¨®n y consideraciones necesarias para interactuar por medio de automatizaciones con Dimmy API._

## Comenzando ??

_En este proyecto se pondr¨¢ en pr¨¢ctica conocimientos para automatizaciones de pruebas aplicadas a servicios y adem¨¢s, la ejecuci¨®n de las pruebas por medio de Jenkins, el cual estar¨¢ en un contenedor de Docker._

_Este proyecto ilustra c¨®mo trabajar con el patr¨®n de automatizaci¨®n Screenplay bajo el framework de automatizaci¨®n llamado Serenity BDD: https://serenity-bdd.info/._



### Pre-requisitos ??

_Todo el proceso de ejecuci¨®n de pruebas se har¨¢ localmente. As¨ª que ten presente los siguientes pre-requisitos para tener una buena experiencia de replicaci¨®n. Adem¨¢s, si no se indica una versi¨®n para alguna herramienta pues deber¨¢s asegurar la versi¨®n m¨¢s reciente._
```
Java 8+
InteliJ IDEA (plugins: Gherkin; Cucumber for Java; Substeps Intellij plugin, Maven)
Maven 3.8.6
Git
Doker
```

### Adecuaci¨®n local del proyecto de automatizaci¨®n ??

_Antes que nada, debes clonar el repositorio en tu equipo. Y si, eso es todo ya que se supone que dispones de los pre-requisitos._


```
git clone https://gitlab.com/ID2A/dummyapi.git
```


_Una vez hecho, ver¨¢s la carpeta "dummyapi" la cual se gener¨® por la clonaci¨®n del repositorio; a su vez contiene, el proyecto de automatizaci¨®n est¨¢ dentro de otra carpeta tambi¨¦n llamada "dummyapi"._

## Ejecutando las pruebas ??

_Primero deber¨¢s abrir el proyecto de automatizaci¨®n:_
* Haga clic en Archivo > Abrir.
* Navegue hasta el archivo pom.xml del proyecto.
* Haga clic en Aceptar.

### Analice las pruebas ??

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

### Ejecute localmente las pruebas y genere un reporte de pruebas ??

_Para ejecutar las pruebas deber¨¢ hacerlo desde una consola o terminal. Navegue hasta donde se encuentre el archivo pom.xml. Ahora, desde la consola:_

_Primero: limpie el proyecto de archivos temporales._
```
mvn clean
```

_Segundo: ejecute una o varias pruebas. Para la ejecuci¨®n de pruebas el comando es diverso. Veamos un ejemplo para ejecutar un runner espec¨ªfico; ser¨¢ el Suite con CucumberWithSerenity. La idea de este formato es ejecutar todos los escenarios deseados desde una Suite._
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

_Tambien se puede ejecutar todo desde una sola l¨ªnea._
```
mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate
```

### Ejecute localmente las pruebas y genere un reporte de pruebas desde un contenedor de Docker con una imagen de Jenkins??
_De antemano ten presente que nos va a interesar guardar las configuraciones de Jenkins en un volumen de Docker externo al contenedor. Una vez que se configure Jenkins no queremos repetir el proceso una y otras vez si por alguna raz¨®n perdemos el contenedor. Mejor tenerlo a la mano para reusar las configuraciones de Jenkins (plugins instalados, pipelines configurados, usuarios, etc.) en un nuevo contenedor._

#### Ejecuci¨®n dede una imagen de Docker ?
* Crea una carpeta por fuera del proyecto llamada "jenkins_home". Yo la crear¨¦ en la carpeta Downloads. En dicha carpeta quedar¨¢n todas las configuraciones que se har¨¢n en Jenkins.
* Crea un Dockerfile en la ra¨ªz del proyecto de automatizaci¨®n (donde est¨¢ el archivo README.md y otros). Usa el block de notas o una herramienta similar para editar. Al final nos interesa tener un archivo que sin extensi¨®n tendr¨¢ el nombre de "Dockerfile".
* Dentro del archivo Dockerfile tendremos la siguiente configuraci¨®n:
```
FROM jenkins/jenkins:latest
MAINTAINER dario696 ddario696@gmail.com
USER root
RUN apt-get update
RUN apt install maven -y
EXPOSE 8080
```

* Para crear la imagen en Docker debes abrir la l¨ªnea de comandos y ubicarte donde est¨¢ el Dockerfile. Ejecuta el siguiente comando.
```
docker build -t jenkins_ci_blueocean .
```

* Cuando el proceso de la consola termine podr¨¢s comprobar que se ha creado una imagen con el nombre "jenkins_ci_blueocean". Usa el sieguiente comando para listar las imagenes de Docker.
```
docker images
```

* Ahora se procede con la creaci¨®n del contenedor.
```
docker run -p 8080:8080 --name dummyapi -d -v C:/Users/pc/Downloads/jenkins_home:/var/jenkins_home jenkins_ci_blueocean
```

* Ahora puedes buscar tu contenedor. Para listar los contenedores creados usa el siguiente comando:
```
docker container ls
```

* Para ejecutar comandos dentro del contenedor debes usar el siguiente comando (usa "exit" para salir del modo interactivo):
```
docker exec -it dummyapi bash
```

* Una vez adentro del contenedor podr¨ªas ejecutar un comando. Por ejemplo verifica la versi¨®n de Java o de Maven.
```
java -version
mvn -version
```

* A estas alturas si abres el navegador web y en la barra de direcci¨®n pones "http://localhost:8080/" ver¨¢s que Jenkin se mostrar¨¢ y pedir¨¢ un password. Usa el modo interactivo para usar el siguiente comando (el c¨®digo lo debes poner donde dice "Administrator password", luego das en continuar):
```
cat /var/jenkins_home/secrets/initialAdminPassword
```

* Jenkins ofrece dos opciones de instalaci¨®n de plugins. Escoge los plugins sugeridos. Espera a que finalice la instalaci¨®n de plugins. Continue con la creaci¨®n de un usuario administrador. Guarde la configuraci¨®n por defecto de la url de Jenkins y finalice. Reinicie Jenkins si es necesario.

* Instale los siguientes plugins: HTML Publisher; Pipeline Maven Integration.

* Cree un pipeline. El nombre queda a su elecci¨®n. En la secci¨®n llamada _Pipeline_: la definici¨®n del pipeline debe ser _Pipeline script from SCM_; en el campo _SCM_ escoge la opción _Git_; en el campo _Repository URL_ pon la url del repositorio que es _https://github.com/ivanarroyave/dummyapi.git_; en la secci¨®n que se llama _Branch Specifier (blank for 'any')_ indica la rama que ser¨¢ la referencia para que se ejecute el pipeline, para este caso pon _*/main_. Finalmente aplica los cambios y guarda el pipeline.

* En la ra¨ªz del proyecto existe un archivo llamado "Jenkinsfile". Dicho archivo contiene una serie de instrucciones en formato declarativo para que se ejecte el pipeline como c¨®digo y adem¨¢s se sincroniza con el pipeline creado en Jenkins en pasos previos. Dale un vistazo al contenido:
```
pipeline {
    agent any
	
	tools { 
        maven 'Maven 3.8.6' 
    }
	
	options {
		// Keep the 10 most recent builds
		buildDiscarder(logRotator(numToKeepStr:'3'))
		skipStagesAfterUnstable()
	}
	
    stages {
        stage("Test") {
            steps {		
				dir("${env.WORKSPACE}/dummyapi"){
					sh "mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate"
					
					 publishHTML(target: [
						reportName : 'Serenity bdd report',
						reportDir:   'target/site/serenity',
						reportFiles: 'index.html',
						keepAll:     true,
						alwaysLinkToLastBuild: true,
						allowMissing: false
					])
				}
			}
        }
    }
}

```

* Para este caso el pipeline necesita Maven 3.8.6. La imagen usada para Docker no la tienen por defecto. Puedes gestionar dicha versi¨®n con Jenkins instalandola desde la administraci¨®n de Jenkins y bajo las configuraciones globales de herramientas. En el apartado de Maven instala la versi¨®n necesaitada.

* Ahora si, finalmente podr¨¢s ejecutar lelos pipeline. Ingres al pipeline y acciona la opci¨®n _Construir ahora_ todas las veces que lo necesites.


## Autores ??

* **Iv¨¢n Dar¨ªo Arroyave Arboleda** - *Ingeniero de sistemas de informaci¨®n - Especializado en el aseguramiento de la calidad del software* - [ivanarroyave](https://github.com/ivanarroyave)

## Expresiones de Gratitud ??

* Comenta a otros sobre este proyecto ??
* Invita una cerveza ?? o un caf¨¦ ? a alguien del equipo. 
* Da las gracias p¨²blicamente ??.

* etc.



---
?? con ?? por [Iv¨¢n Dar¨ªo Arroyave Arboleda](https://github.com/ivanarroyave) ??
