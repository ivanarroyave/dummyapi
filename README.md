# Automatización de servicios - Dummy API

A continuación se explicara la composición y consideraciones necesarias para interactuar por medio de automatizaciones con Dimmy API.

## Comenzando

En este proyecto se pondrá en práctica conocimientos para automatizaciones de pruebas aplicadas a servicios y además, la ejecución de las pruebas por medio de Jenkins, el cual estará en un contenedor de Docker.

Este proyecto ilustra cómo trabajar con el patrón de automatización Screenplay bajo el framework de automatización llamado Serenity BDD: https://serenity-bdd.info/.

### Pre-requisitos

Todo el proceso de ejecución de pruebas se hará localmente. Ten presente los siguientes pre-requisitos para tener una buena experiencia de replicación. Además, si no se indica una versión para alguna herramienta pues deberás asegurar la versión más reciente.
```
Java 8+
InteliJ IDEA (plugins: Gherkin; Cucumber for Java; Substeps Intellij plugin, Maven)
Maven 3.8.6
Git
Doker
```

### Adecuación local del proyecto de automatización

Antes que nada, debes clonar el repositorio en tu equipo. Y si, eso es todo ya que se supone que dispones de los pre-requisitos.


```
git clone https://github.com/ivanarroyave/dummyapi.git
```


Una vez hecho, verás la carpeta "dummyapi" la cual se generó por la clonación del repositorio; a su vez contiene, el proyecto de automatización está dentro de otra carpeta también llamada "dummyapi".

## Ejecutando las pruebas

Primero deberás abrir el proyecto de automatización:
* Haga clic en Archivo > Abrir.
* Navegue hasta el archivo pom.xml del proyecto.
* Haga clic en Aceptar.

### Analice las pruebas

Las pruebas en este proyecto se basan en las funcionaliadaes descritas en https://dummyapi.io/docs/user y https://dummyapi.io/docs/errors.
Se han automatizado un todal de 11 escenarios de pruebas entre rutas ideales y alternas. Las descripciones de los Features y sus respectivos Scenarios las puede encontrar en la ruta: "src/test/resources/features/user".

Por cada Feature existe una clase runner en la ruta "src/test/java/io/dummyapi/runners/".
```
* CreateUserRunner.
* DeleteUserRunner.
* SearchUserRunner
* UpdateUserRunner.
```

Existen dos runner adicionales que representan una Suite de runners. Uno con Cucumber con Serenity y otro con Junit.
```
* AnExampleOfGeneralExecutorOfTest. Forma de Suite con CucumberWithSerenity.
* DeleteUserRunner. Forma de Suite con Junit.
```

### Ejecute localmente las pruebas y genere un reporte de pruebas

Para ejecutar las pruebas deberá hacerlo desde una consola o terminal. Navegue hasta donde se encuentre el archivo pom.xml. Ahora, desde la consola:

Limpie el proyecto de archivos temporales.
```
mvn clean
```

Ejecute una o varias pruebas. Para la ejecución de pruebas el comando es diverso. Veamos un ejemplo para ejecutar un runner específico; será el Suite con CucumberWithSerenity. La idea de este formato es ejecutar todos los escenarios deseados desde una Suite.
```
mvn test -Dtest=AnExampleOfGeneralExecutorOfTest
```

Si desea ejecutar TODAS las pruebas use el siguiente comando:
```
mvn verify
```

Para generar el reporte de pruebas. El reporte se genera normalmente en la ruta "./dummyapi/target/site/serenity/index.html". Para verlo, debe abrir el archivo "index.html".
```
mvn serenity:aggregate
```

Tambien se puede ejecutar todo desde una sola línea.
```
mvn clean test -Dtest=AnExampleOfGeneralExecutorOfTest serenity:aggregate
```

### Ejecute localmente las pruebas y genere un reporte de pruebas desde un contenedor de Docker con una imagen de Jenkins
De antemano ten presente que nos va a interesar guardar las configuraciones de Jenkins en un volumen de Docker externo al contenedor. Una vez que se configure Jenkins no queremos repetir el proceso una y otras vez si por alguna razón perdemos el contenedor. Mejor tenerlo a la mano para reusar las configuraciones de Jenkins (plugins instalados, pipelines configurados, usuarios, etc.) en un nuevo contenedor.

#### Ejecución dede una imagen de Docker
* Crea una carpeta por fuera del proyecto llamada "jenkins_home". Yo la crearé en la carpeta Downloads. En dicha carpeta quedarán todas las configuraciones que se harán en Jenkins.
* Crea un Dockerfile en la raíz del proyecto de automatización (donde está el archivo README.md y otros). Usa el block de notas o una herramienta similar para editar. Al final nos interesa tener un archivo que sin extensión tendrá el nombre de "Dockerfile".
* Dentro del archivo Dockerfile tendremos la siguiente configuración:
```
FROM jenkins/jenkins:latest
MAINTAINER dario696 ddario696@gmail.com
USER root
RUN apt-get update
RUN apt install maven -y
EXPOSE 8080
```

* Para crear la imagen en Docker debes abrir la línea de comandos y ubicarte donde está el Dockerfile. Ejecuta el siguiente comando.
```
docker build -t jenkins_lulado .
```

* Cuando el proceso de la consola termine podrás comprobar que se ha creado una imagen con el nombre "jenkins_lulado". Usa el sieguiente comando para listar las imagenes de Docker.
```
docker images
```

* Ahora se procede con la creación del contenedor.
```
docker run -p 8080:8080 --name dummyapi -d -v C:/Users/pc/Downloads/jenkins_home:/var/jenkins_home jenkins_lulado
```

* Ahora puedes buscar tu contenedor. Para listar los contenedores creados usa el siguiente comando:
```
docker container ls
```

* Para ejecutar comandos dentro del contenedor debes usar el siguiente comando (usa "exit" para salir del modo interactivo):
```
docker exec -it dummyapi bash
```

* Una vez adentro del contenedor podrías ejecutar un comando. Por ejemplo verifica la versión de Java o de Maven.
```
java -version
mvn -version
```

* A estas alturas si abres el navegador web y en la barra de dirección pones "http://localhost:8080/" verás que Jenkin se mostrará y pedirá un password. Usa el modo interactivo para usar el siguiente comando (el código lo debes poner donde dice "Administrator password", luego das en continuar):
```
cat /var/jenkins_home/secrets/initialAdminPassword
```

* Jenkins ofrece dos opciones de instalación de plugins. Escoge los plugins sugeridos. Espera a que finalice la instalación de plugins. Continue con la creación de un usuario administrador. Guarde la configuración por defecto de la url de Jenkins y finalice. Reinicie Jenkins si es necesario.

* Instale los siguientes plugins: HTML Publisher; Pipeline Maven Integration.

* Cree un pipeline. El nombre queda a su elección. En la sección llamada _Pipeline_: la definición del pipeline debe ser _Pipeline script from SCM_; en el campo _SCM_ escoge la opción _Git_; en el campo _Repository URL_ pon la url del repositorio que es _https://github.com/ivanarroyave/dummyapi.git_; en la sección que se llama _Branch Specifier (blank for 'any')_ indica la rama que será la referencia para que se ejecute el pipeline, para este caso pon _*/main_. Finalmente aplica los cambios y guarda el pipeline.

* En la raíz del proyecto existe un archivo llamado "Jenkinsfile". Dicho archivo contiene una serie de instrucciones en formato declarativo para que se ejecte el pipeline como código y además se sincroniza con el pipeline creado en Jenkins en pasos previos. Dale un vistazo al contenido:
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

* Para este caso el pipeline necesita Maven 3.8.6. La imagen usada para Docker no la tienen por defecto. Puedes gestionar dicha versión con Jenkins instalandola desde la administración de Jenkins y bajo las configuraciones globales de herramientas. En el apartado de Maven instala la versión necesaitada.

* Ahora si, finalmente podrás ejecutar lelos pipeline. Ingres al pipeline y acciona la opción _Construir ahora_ todas las veces que lo necesites.


## Autores

* **Iván Darío Arroyave Arboleda** - *Ingeniero de sistemas de información - Especializado en el aseguramiento de la calidad del software* - [ivanarroyave](https://github.com/ivanarroyave)

## Expresiones de Gratitud

* Comenta a otros sobre este proyecto
* Invita una cerveza o un café a alguien del equipo. 
* Da las gracias públicamente.
* etc.
