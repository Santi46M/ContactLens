ContactLens – Calculadora de lentes de contacto

Aplicación de escritorio desarrollada en Java para el cálculo de curvas en lentes de contacto rígidos, con persistencia de datos y visualización gráfica de los resultados.

Descripción
Este proyecto surge de una necesidad para modelar y automatizar cálculos ópticos a partir de parámetros básicos como curva base, graduación y material del lente.
A partir de estos datos, la aplicación calcula las curvas resultantes y una aproximación de la torsión, además de representar gráficamente los ejes principales.
El sistema también guarda un historial de cálculos en una base de datos local, lo que permite consultar y filtrar resultados anteriores.

Funcionalidades
Cálculo de:
Curva esférica
Curva cilíndrica
Torsión aproximada
Selección de materiales predefinidos o ingreso manual de índice
Representación gráfica del lente en función del eje y las curvas
Persistencia de cálculos en base de datos
Visualización y búsqueda en historial
Arquitectura

El proyecto está organizado separando responsabilidades en distintas capas, siguiendo una estructura similar a MVC:

ui: interfaz gráfica construida con Swing
controller: manejo de eventos y coordinación entre capas
service: lógica de cálculo y validaciones
repository: acceso a base de datos mediante JDBC
model: entidades del dominio
db: configuración e inicialización de la base de datos

Esta separación permite mantener el código más ordenado, desacoplado y fácil de extender.

Tecnologías utilizadas
Java 21
Swing (Java2D para el renderizado del diagrama)
JDBC
HSQLDB (base de datos embebida)
Ejecución
Clonar el repositorio
Abrir el proyecto en IntelliJ IDEA
Agregar hsqldb.jar como dependencia del módulo
Ejecutar la clase principal:

app.Main

La base de datos se crea automáticamente en la primera ejecución.
Notas técnicas
El cálculo de curvas se basa en fórmulas que dependen del índice del material y la graduación.
La torsión se calcula mediante una aproximación en función del cilindro y la potencia media.
El diagrama se dibuja utilizando Graphics2D, representando los ejes principales y sus curvas asociadas.
El historial se guarda en una base HSQLDB en modo archivo local.

Posibles mejoras
Exportación del historial (CSV o Excel)
Soporte para múltiples usuarios
Mejora en validaciones y manejo de errores
Configuración de materiales desde base de datos
Versión web (por ejemplo con Spring Boot)
Tests unitarios para la lógica de cálculo

El objetivo principal fue construir una aplicación completa de escritorio en Java sin utilizar frameworks externos, enfocándome en:
Separación de responsabilidades
Persistencia de datos
Manejo de interfaz gráfica
Implementación de lógica de dominio
