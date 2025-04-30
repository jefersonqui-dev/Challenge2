# 🚦  Sistema de Gestión de Emergencias Urbanas


Este proyecto es una aplicación de consola simple diseñada para simular un sistema básico de gestión y despacho de recursos (vehículos, personal) para atender emergencias urbanas (incendios, accidentes, robos, etc.). El objetivo principal de su desarrollo ha sido **aprender y aplicar conceptos fundamentales de Programación Orientada a Objetos (POO)** y **Patrones de Diseño** en un contexto práctico.

La aplicación permite registrar diferentes tipos de emergencias con distintos niveles de gravedad, visualizar el estado de los recursos disponibles, "atender" emergencias (lo que activa un proceso de asignación de recursos), ver estadísticas básicas de la jornada y finalizar la operación.

## 📚 Objetivos de Aprendizaje y Conceptos de POO

El diseño y la implementación de este sistema se centraron en los siguientes conceptos clave de POO:

1.  📦 **Encapsulamiento:** Los datos (atributos) de cada entidad (Recurso, Emergencia, Ubicacion) se mantienen privados dentro de sus clases y solo se accede a ellos o se modifican a través de métodos públicos (getters y setters). Esto protege la integridad de los datos y oculta los detalles de implementación interna.
    * *Ejemplos:* Atributos `private` en `Recurso`, `Emergencia`, `Ubicacion`, y métodos `public get...()`, `set...()`.

2.  🌳 **Herencia:** Se utiliza para modelar relaciones "es un/a" y reutilizar código común.
    * La clase base `Recurso` define las propiedades y comportamientos comunes a todos los recursos (ID, tipo, ubicación, disponibilidad).
    * Clases concretas como `CamionBomberos`, `AmbulanciaVehiculo`, `Patrulla`, `BomberoPersonal`, etc., heredan de `Recurso` (`extends Recurso`), reutilizando su estructura y añadiendo (potencialmente) atributos o comportamientos específicos.

3. 🎭 **Polimorfismo:** Permite tratar objetos de diferentes clases de manera uniforme a través de una superclase o interfaz común.
    * La interfaz `TipoEmergencia` define un contrato (como `calcularRecursosInicialesNecesarios()`). Clases como `Incendio`, `AccidenteVehicular`, `Robo` implementan esta interfaz (`implements TipoEmergencia`), proporcionando su lógica específica. El sistema puede llamar a `emergencia.getTipo().calcularRecursosInicialesNecesarios(...)` sin saber si es un Incendio o un Robo, y se ejecutará la lógica correcta para ese tipo.
    * La interfaz `GestionableEmergencia` define acciones de servicio (`asignarRecursos`, `resolverEmergencia`). Clases como `BomberoService`, `AmbulanciaService` la implementan. El sistema podría interactuar con una lista de `GestionableEmergencia`s llamando a sus métodos de forma polimórfica.
    * La interfaz `EstrategiaPriorizacion` define el algoritmo de ordenamiento (`priorizar()`). Clases concretas implementan la lógica específica (por gravedad, antigüedad, cercanía). El Sistema de Gestión usa la estrategia activa llamando a `estrategiaActual.priorizar(...)` polimórficamente.
    * La interfaz `ObservadorEmergencia` define el método de notificación (`actualizar()`). Las clases observadoras la implementan, permitiendo que el Sujeto (`SistemaGestionEmergencia`) notifique sin conocer su tipo específico.

4. ☁️ **Abstracción:** Se enfoca en los aspectos esenciales de una entidad u operación, ocultando los detalles complejos. Las interfaces (`TipoEmergencia`, `GestionableEmergencia`, `EstrategiaPriorizacion`, `ObservadorEmergencia`) son formas clave de abstracción, definiendo "qué" se puede hacer sin especificar "cómo".

## 🧩 Patrones de Diseño Implementados

Este proyecto integra varios patrones de diseño para resolver problemas de diseño comunes de manera flexible y mantenible:

1.  ☝️ **Singleton:**
    * **Propósito:** Asegurar que una clase tenga solo una instancia y proporcionar un punto de acceso global a ella.
    * **Aplicación:** La clase `SistemaGestionEmergencia` implementa Singleton (`private static instance`, `private constructor`, `public static getInstance()`). Es el cerebro central que gestiona todos los recursos, emergencias y servicios. Es lógico tener solo una instancia de la central de despacho.

2. 🏭 **Factory Method (o Static Factory Method):**
    * **Propósito:** Centralizar la lógica de creación de objetos, delegando la responsabilidad de la instanciación a un método dedicado.
    * **Aplicación:** La clase `EmergenciaFactory` con su método estático `crearEmergencia(...)`. En lugar de llamar `new Emergencia(...)` directamente en el código cliente (como `MainApp`), se llama a `EmergenciaFactory.crearEmergencia(...)`. Esto desacopla el cliente de la creación y facilita cambiar la lógica de creación o añadir tipos de emergencia en el futuro.

3. 🗺️ **Strategy:**
    * **Propósito:** Definir una familia de algoritmos (estrategias), encapsular cada uno y hacerlos intercambiables en tiempo de ejecución.
    * **Aplicación:** Se utiliza para la priorización de emergencias. La interfaz `EstrategiaPriorizacion` define el contrato (`priorizar()`). Clases como `PrioridadPorGravedad`, `PrioridadPorAntiguedad`, `PrioridadPorCercania` implementan diferentes lógicas de ordenamiento. `SistemaGestionEmergencia` (el contexto) tiene una referencia a la estrategia actual y la usa (`estrategiaActual.priorizar(...)`) al devolver la lista de emergencias activas (Opción 3), permitiendo cambiar dinámicamente el criterio de ordenación.

4. 🔔 **Observer:**
    * **Propósito:** Definir una dependencia uno-a-muchos entre objetos para que cuando un objeto (sujeto) cambia de estado, todos sus dependientes (observadores) sean notificados automáticamente.
    * **Aplicación:** `SistemaGestionEmergencia` actúa como el Sujeto. Mantiene una lista de `ObservadorEmergencia`s registrados (`agregarObservador`, `removerObservador`). Cuando ocurre un evento relevante (ej: `registrarEmergencia()`), el Sujeto llama al método `actualizar()` de cada observador. Esto permite que otras partes del sistema (como `ConsoleNotificationObserver` para mostrar notificaciones) reaccionen a los eventos del sistema de forma desacoplada.

## 🔑 Otros Conceptos y Componentes Clave

*▶️ **Lambdas y Streams:** Se utilizan para operaciones funcionales en colecciones, haciendo el código más conciso y legible para tareas como filtrar recursos disponibles por tipo y ordenar listas de recursos o emergencias por distancia o criterios de prioridad.
    * *Ejemplos:* `lista.stream().filter(...).collect()`, `lista.stream().sorted(...).collect()`.
* 🛠️**Clase de Utilidad (`MapaUrbano`):** Una clase con métodos estáticos (`calcularDistanciaManhattan`) para realizar tareas genéricas que no requieren mantener estado, como cálculos geográficos simples.
* 🚦**Enums:** Se utilizan para definir conjuntos fijos de constantes (`TipoRecurso`, `NivelGravedad`, `EstadoEmergencia`), mejorando la seguridad de tipo y la legibilidad.
* 🧠**Gestión de Estado Centralizada:** El Singleton `SistemaGestionEmergencia` maneja las listas principales de `emergenciasActivas`, `emergenciasResueltas`, `recursosDisponibles` y `recursosOcupados`, siendo el punto central de verdad del sistema.
* 🧱**Separación de Responsabilidades:** La aplicación se divide en capas: la UI (`ConsoleUI`, `MainApp`) maneja la interacción con el usuario, el Singleton (`SistemaGestionEmergencia`) maneja la lógica de negocio central, y otras clases representan las entidades (`Recurso`, `Emergencia`, `Ubicacion`), los tipos (`TipoRecurso`, `TipoEmergencia`s concretas), los patrones (`Factory`, `Strategy`, `Observer` y sus implementaciones) y las utilidades (`MapaUrbano`).

## ⚙️Cómo Compilar y Ejecutar

1.  ✅Asegúrate de tener un entorno de desarrollo Java (JDK) instalado (Java 8 o superior recomendado).
2.  📁Navega a la carpeta `src` dentro de tu proyecto en la terminal.
3.  💻Compila el archivo Java:

    ```bash
    MainApp.java
    ```
    
4.  🚀Ejecuta la aplicación:
    ```bash
    java com.yourcompany.ues.main.MainApp
    ```
    (Reemplaza `com/yourcompany/ues` con tu nombre de paquete real).
    
    De lo contrario simplemente ejecuta:
      ```bash
    java com.jquiguantar.ues.main.MainApp
    ```

## 📋Cómo Usar la Aplicación (Menú)

Una vez ejecutada, la aplicación mostrará un menú en consola:

1.  ✨ **Registrar Emergencia:** Permite ingresar los datos (Tipo, Ubicación por coordenadas X Y, Nivel de Gravedad) para crear una nueva emergencia en el sistema.
2.  📊 **Ver el Estado Actual de Recursos:** Muestra una lista de todos los recursos (vehículos y personal) indicando cuáles están Disponibles y cuáles están Ocupados.
3.  ➡️ **Atender Emergencia:** Muestra las emergencias activas (pendientes o en progreso). Permite ingresar el ID de una emergencia para intentar asignarle los recursos necesarios. La asignación priorizará los recursos disponibles más cercanos.
4.  📈 **Mostrar Estadísticas del Día:** Simula la resolución de algunas emergencias en progreso y muestra estadísticas como el total de emergencias atendidas y el tiempo promedio de respuesta por tipo.
5.  👋 **Finalizar la jornada y salir:** Simula la resolución final de emergencias pendientes, muestra las estadísticas finales y cierra la aplicación.

---