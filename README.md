# Automatizaci車n de servicios - Dummy API

_A continuaci車n se explicara la composici車n y consideraciones necesarias para interactuar por medio de automatizaciones con Dimmy API._

## Comenzando ??

_En este proyecto se pondr芍 en pr芍ctica conocimientos para automatizaciones de pruebas aplicadas a servicios y adem芍s, la ejecuci車n de las pruebas por medio de Jenkins, el cual estar芍 en un contenedor de Docker._

_Este proyecto ilustra c車mo trabajar con el patr車n de automatizaci車n Screenplay bajo el framework de automatizaci車n llamado Serenity BDD: https://serenity-bdd.info/._



### Pre-requisitos ??

_Todo el proceso de ejecuci車n de pruebas se har芍 localmente. As赤 que ten presente los siguientes pre-requisitos para tener una buena experiencia de replicaci車n. Adem芍s, si no se indica una versi車n para alguna herramienta pues deber芍s asegurar la versi車n m芍s reciente._
```
Java 8+
InteliJ IDEA (plugins: Gherkin; Cucumber for Java; Substeps Intellij plugin, Maven)
Maven 3.8.6
Git
Doker
```

### Adecuaci車n local del proyecto de automatizaci車n ??

_Antes que nada, debes clonar el repositorio en tu equipo. Y si, eso es todo ya que se supone que dispones de los pre-requisitos._


```
git clone https://gitlab.com/ID2A/dummyapi.git
```


_Una vez hecho, ver芍s la carpeta "dummyapi" la cual se gener車 por la clonaci車n del repositorio; a su vez contiene, el proyecto de automatizaci車n est芍 dentro de otra carpeta tambi谷n llamada "dummyapi"._

## Ejecutando las pruebas ??

_Primero deber芍s abrir el proyecto de automatizaci車n:_
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

_Para ejecutar las pruebas deber芍 hacerlo desde una consola o terminal. Navegue hasta donde se encuentre el archivo pom.xml. Ahora, desde la consola:_

_Primero: limpie el proyecto de archivos temporales._
```
mvn clean
```

_Segundo: ejecute una o varias pruebas. Para la ejecuci車n de pruebas el comando es diverso. Veamos un ejemplo para ejecutar un runner espec赤fico; ser芍 el Suite con CucumberWithSerenity. La idea de este formato es ejecutar todos los escenarios deseados desde una Suite._
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

_Tambien se puede ejecutar todo desde una sola l赤nea._
```
mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate
```

### Ejecute localmente las pruebas y genere un reporte de pruebas desde un contenedor de Docker con una imagen de Jenkins??
_De antemano ten presente que nos va a interesar guardar las configuraciones de Jenkins en un volumen de Docker externo al contenedor. Una vez que se configure Jenkins no queremos repetir el proceso una y otras vez si por alguna raz車n perdemos el contenedor. Mejor tenerlo a la mano para reusar las configuraciones de Jenkins (plugins instalados, pipelines configurados, usuarios, etc.) en un nuevo contenedor._

#### Ejecuci車n dede una imagen de Docker ?
* Crea una carpeta por fuera del proyecto llamada "jenkins_home". Yo la crear谷 en la carpeta Downloads. En dicha carpeta quedar芍n todas las configuraciones que se har芍n en Jenkins.
* Crea un Dockerfile en la ra赤z del proyecto de automatizaci車n (donde est芍 el archivo README.md y otros). Usa el block de notas o una herramienta similar para editar. Al final nos interesa tener un archivo que sin extensi車n tendr芍 el nombre de "Dockerfile".
* Dentro del archivo Dockerfile tendremos la siguiente configuraci車n:
```
FROM jenkins/jenkins:latest
MAINTAINER dario696 ddario696@gmail.com
USER root
RUN apt-get update
RUN apt install maven -y
EXPOSE 8080
```

* Para crear la imagen en Docker debes abrir la l赤nea de comandos y ubicarte donde est芍 el Dockerfile. Ejecuta el siguiente comando.
```
docker build -t jenkins_ci_blueocean .
```

* Cuando el proceso de la consola termine podr芍s comprobar que se ha creado una imagen con el nombre "jenkins_ci_blueocean". Usa el sieguiente comando para listar las imagenes de Docker.
```
docker images
```

* Ahora se procede con la creaci車n del contenedor.
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

* Una vez adentro del contenedor podr赤as ejecutar un comando. Por ejemplo verifica la versi車n de Java o de Maven.
```
java -version
mvn -version
```

* A estas alturas si abres el navegador web y en la barra de direcci車n pones "http://localhost:8080/" ver芍s que Jenkin se mostrar芍 y pedir芍 un password. Usa el modo interactivo para usar el siguiente comando (el c車digo lo debes poner donde dice "Administrator password", luego das en continuar):
```
cat /var/jenkins_home/secrets/initialAdminPassword
```

* Jenkins ofrece dos opciones de instalaci車n de plugins. Escoge los plugins sugeridos. Espera a que finalice la instalaci車n de plugins. Continue con la creaci車n de un usuario administrador. Guarde la configuraci車n por defecto de la url de Jenkins y finalice. Reinicie Jenkins si es necesario.

* Instale los siguientes plugins: HTML Publisher; Pipeline Maven Integration.

* Cree un pipeline. El nombre queda a su elecci車n. En la secci車n llamada _Pipeline_: la definici車n del pipeline debe ser _Pipeline script from SCM_; en el campo _SCM_ escoge la opci車n _Git_; en el campo _Repository URL_ pon la url del repositorio que es _https://gitlab.com/ID2A/dummyapi.git_; en la secci車n que se llama _Branch Specifier (blank for 'any')_ indica la rama que ser芍 la referencia para que se ejecute el pipeline, para este caso pon _*/main_. Finalmente aplica los cambios y guarda el pipeline.

* En la ra赤z del proyecto existe un archivo llamado "Jenkinsfile". Dicho archivo contiene una serie de instrucciones en formato declarativo para que se ejecte el pipeline como c車digo y adem芍s se sincroniza con el pipeline creado en Jenkins en pasos previos. Dale un vistazo al contenido:
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

* Para este caso el pipeline necesita Maven 3.8.6. La imagen usada para Docker no la tienen por defecto. Puedes gestionar dicha versi車n con Jenkins instalandola desde la administraci車n de Jenkins y bajo las configuraciones globales de herramientas. En el apartado de Maven instala la versi車n necesaitada.

* Ahora si, finalmente podr芍s ejecutar lelos pipeline. Ingres al pipeline y acciona la opci車n _Construir ahora_ todas las veces que lo necesites.


## Autores ??

* **Iv芍n Dar赤o Arroyave Arboleda** - *Ingeniero de sistemas de informaci車n - Especializado en el aseguramiento de la calidad del software* - [ID2A](https://gitlab.com/ID2A)

## Expresiones de Gratitud ??

* Comenta a otros sobre este proyecto ??
* Invita una cerveza ?? o un caf谷 ? a alguien del equipo. 
* Da las gracias p迆blicamente ??.

* etc.



---
?? con ?? por [Iv芍n Dar赤o Arroyave Arboleda](https://gitlab.com/ID2A) ??