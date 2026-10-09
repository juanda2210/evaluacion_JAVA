# CrediYA S.A.S. 💳

Sistema de gestión de préstamos desarrollado en **Java**, con persistencia de datos en **MySQL** y una interfaz de consola.

El proyecto permite administrar empleados, clientes, préstamos y pagos, además de generar reportes y exportar información a archivos de texto.

---

## 📋 Tabla de contenidos

- [Descripción](#-descripción)
- [Objetivos](#-objetivos)
- [Tecnologías](#-tecnologías)
- [Arquitectura](#-arquitectura)
- [Estructura del proyecto](#-estructura-del-proyecto)
- [Módulos principales](#-módulos-principales)
- [Base de datos](#-base-de-datos)
- [Flujo general](#-flujo-general)
- [Funcionalidades](#-funcionalidades)
- [Exportación de información](#-exportación-de-información)
- [Configuración](#-configuración)
- [Ejecución](#-ejecución)
- [Conceptos de Java aplicados](#-conceptos-de-java-aplicados)
- [Autor](#-autor)

---

## 📖 Descripción

**CrediYA S.A.S.** es una aplicación de consola orientada a la gestión básica de una entidad financiera.

El sistema permite registrar y consultar información de:

- 👨‍💼 Empleados
- 👤 Clientes
- 💰 Préstamos
- 💵 Pagos

También permite:

- Simular préstamos.
- Cambiar el estado de los préstamos.
- Registrar abonos.
- Consultar saldos pendientes.
- Consultar históricos de pagos.
- Identificar clientes morosos.
- Consultar préstamos activos y vencidos.
- Exportar registros a archivos `.txt`.

El proyecto utiliza **JDBC** para comunicarse con MySQL y organiza la lógica mediante modelos, DAO, vistas y clases utilitarias.

---

## 🎯 Objetivos

El proyecto fue desarrollado como ejercicio práctico para aplicar conceptos fundamentales de desarrollo backend con Java:

- Programación Orientada a Objetos.
- Separación de responsabilidades.
- Manejo de colecciones.
- Streams de Java.
- Excepciones.
- JDBC.
- Consultas SQL parametrizadas.
- Manejo de fechas con `LocalDate`.
- Persistencia en MySQL.
- Lectura y escritura de archivos.
- Menús interactivos por consola.
- Organización de proyectos mediante paquetes.

---

## 🛠️ Tecnologías

| Tecnología | Uso |
|---|---|
| Java | Lenguaje principal |
| MySQL | Base de datos |
| JDBC | Conexión entre Java y MySQL |
| Maven | Gestión del proyecto y dependencias |
| IntelliJ IDEA | Entorno de desarrollo |
| Git / GitHub | Control de versiones |
| Java Streams | Filtrado y procesamiento de colecciones |
| `java.time.LocalDate` | Manejo de fechas |
| `java.nio.file` | Exportación a archivos |

---

## 🏗️ Arquitectura

El proyecto utiliza una estructura separada por responsabilidades:

```text
Main
  │
  ▼
Views
  │
  ▼
DAO
  │
  ▼
ConexionDB
  │
  ▼
MySQL
```

Los modelos representan las entidades principales del sistema:

```text
Cliente
Empleado
Prestamo
Pago
Simulacion
```

### Capas principales

#### `Main`

Punto de entrada de la aplicación.

Se encarga de mostrar el menú principal y dirigir al usuario hacia cada módulo.

#### `views`

Contiene la interacción con el usuario.

```text
EmpleadosView
ClientesView
PrestamosView
PagosView
ReportesView
```

Las vistas capturan información mediante `ScannerUtils` y utilizan los DAO para ejecutar las operaciones necesarias.

#### `dao`

Contiene la lógica de acceso a datos.

```text
EmpleadoDAO
ClienteDAO
PrestamoDAO
PagosDAO
```

Aquí se encuentran las consultas SQL y las operaciones CRUD relacionadas con la base de datos.

#### `models`

Representa las entidades y reglas básicas del dominio.

```text
Empleado
Cliente
Prestamo
Pago
Simulacion
```

#### `database`

Contiene la clase responsable de crear las conexiones con MySQL.

```text
ConexionDB
```

#### `util`

Contiene herramientas reutilizables:

```text
ScannerUtils
FileUtils
```

---

## Diagrama UML

![Diagrama UML del sistema CrediYa](images/diagramaUML.png)

## 📁 Estructura del proyecto

```text
CrediYA_S.A.S/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── apex/
│                   │
│                   ├── Main.java
│                   │
│                   ├── dao/
│                   │   ├── ClienteDAO.java
│                   │   ├── EmpleadoDAO.java
│                   │   ├── PagosDAO.java
│                   │   └── PrestamoDAO.java
│                   │
│                   ├── database/
│                   │   └── ConexionDB.java
│                   │
│                   ├── models/
│                   │   ├── Cliente.java
│                   │   ├── Empleado.java
│                   │   ├── Pago.java
│                   │   ├── Prestamo.java
│                   │   └── Simulacion.java
│                   │
│                   ├── util/
│                   │   ├── FileUtils.java
│                   │   └── ScannerUtils.java
│                   │
│                   └── views/
│                       ├── ClientesView.java
│                       ├── EmpleadosView.java
│                       ├── PagosView.java
│                       ├── PrestamosView.java
│                       └── ReportesView.java
│
├── clientes.txt
├── empleados.txt
├── prestamos.txt
├── pagos.txt
├── pom.xml
└── README.md
```

> Los archivos `.txt` se generan cuando se utiliza la funcionalidad de exportación.

---

## 🧩 Módulos principales

### 👨‍💼 Empleados

Permite:

1. Registrar empleados.
2. Consultar empleados por ID.
3. Exportar empleados a archivo.

Cada empleado contiene:

```text
id
nombre
documento
rol
correo
salario
```

---

### 👤 Clientes

Permite:

1. Registrar clientes.
2. Listar clientes.
3. Consultar préstamos de un cliente.
4. Definir un cliente como moroso.
5. Exportar clientes a archivo.

Cada cliente contiene:

```text
id
nombre
documento
correo
telefono
situacionCrediticia
```

La situación crediticia inicial de un cliente nuevo es:

```text
Puntual
```

---

### 💰 Préstamos

Permite:

1. Crear préstamos.
2. Realizar simulaciones.
3. Cambiar el estado de un préstamo.
4. Exportar préstamos a archivo.

Un préstamo contiene:

```text
id
cliente_id
empleado_id
monto
interes
cuotas
fecha_inicio
estado
```

Los nuevos préstamos se crean inicialmente con estado:

```text
Pendiente
```

---

### 💵 Pagos

Permite:

1. Registrar abonos.
2. Consultar saldo pendiente.
3. Consultar histórico de pagos.
4. Exportar pagos a archivo.

Cada pago contiene:

```text
id
prestamo_id
fecha_pago
monto
```

El sistema verifica que un abono no supere el saldo pendiente del préstamo.

---

### 📊 Reportes

El módulo de reportes permite consultar:

- Préstamos activos.
- Préstamos vencidos.
- Clientes morosos.

---

## 💡 Simulación de préstamos

La clase `Simulacion` se encarga de realizar los cálculos asociados al préstamo.

Actualmente utiliza una tasa fija anual:

```text
19.5%
```

A partir del monto y el número de meses calcula:

- Tasa de interés anual.
- Tasa de interés mensual.
- Interés anual.
- Interés mensual.
- Cuota mensual.
- Total a pagar.

Ejemplo conceptual:

```text
Monto: $1.000.000
Periodo: 12 meses

Tasa anual: 19.5%
Tasa mensual: 1.625%

Interés anual: $195.000
Total a pagar: $1.195.000
```

---

## 🗄️ Base de datos

El proyecto utiliza MySQL como sistema de gestión de base de datos.

Base de datos utilizada:

```text
crediya_db
```

### Tabla `empleados`

```text
id
nombre
documento
rol
correo
salario
```

### Tabla `clientes`

```text
id
nombre
documento
correo
telefono
situacion_crediticia
```

### Tabla `prestamos`

```text
id
cliente_id
empleado_id
monto
interes
cuotas
fecha_inicio
estado
```

Relaciones:

```text
clientes 1 ──────── N prestamos
empleados 1 ─────── N prestamos
```

### Tabla `pagos`

```text
id
prestamo_id
fecha_pago
monto
```

Relación:

```text
prestamos 1 ──────── N pagos
```

### Modelo relacional

```text
┌──────────────┐
│  EMPLEADOS   │
├──────────────┤
│ id           │
│ nombre       │
│ documento    │
│ rol          │
│ correo       │
│ salario      │
└──────┬───────┘
       │
       │ 1:N
       ▼
┌────────────────────┐
│     PRESTAMOS      │
├────────────────────┤
│ id                 │
│ cliente_id         │
│ empleado_id        │
│ monto              │
│ interes            │
│ cuotas             │
│ fecha_inicio       │
│ estado             │
└─────────┬──────────┘
          │
          │ 1:N
          ▼
┌────────────────────┐
│       PAGOS        │
├────────────────────┤
│ id                 │
│ prestamo_id        │
│ fecha_pago         │
│ monto              │
└────────────────────┘

┌──────────────┐
│   CLIENTES   │
├──────────────┤
│ id           │
│ nombre       │
│ documento    │
│ correo       │
│ telefono     │
│ situación    │
└──────┬───────┘
       │
       │ 1:N
       ▼
    PRESTAMOS
```

---

## 🔄 Flujo general de la aplicación

Al iniciar la aplicación se muestra:

```text
===== MENÚ PRINCIPAL =====
1. Empleados
2. Clientes
3. Préstamos
4. Pagos
5. Reportes
6. Salir del programa
```

El usuario selecciona un módulo y el sistema dirige la ejecución a la vista correspondiente.

Ejemplo:

```text
Main
 ↓
PrestamosView
 ↓
PrestamoDAO
 ↓
ConexionDB
 ↓
MySQL
```

---

## ⚙️ Funcionalidades principales

### Registrar empleado

El sistema solicita:

```text
Nombre
Documento
Rol
Correo
Salario
```

Posteriormente crea el objeto `Empleado` y lo almacena en MySQL.

### Registrar cliente

Solicita:

```text
Nombre
Documento
Correo
Telefono
```

El cliente se crea inicialmente con situación crediticia:

```text
Puntual
```

### Crear préstamo

Para crear un préstamo se solicita:

```text
Nombre del cliente
Nombre del empleado
Monto
Periodo en meses
```

El sistema obtiene los IDs correspondientes y crea una simulación antes de registrar el préstamo.

### Registrar abono

El sistema solicita:

```text
Monto
ID del préstamo
```

Después calcula:

```text
saldo actual
-
nuevo abono
=
saldo restante
```

Si el abono supera el saldo pendiente, la operación es rechazada.

### Consultar saldo

El saldo se obtiene sumando los pagos realizados:

```text
saldo pendiente = monto del préstamo - total de pagos
```

### Cambiar estado

El estado del préstamo puede actualizarse desde el módulo correspondiente.

Actualmente, la operación de cambio de estado utilizada por el sistema establece:

```text
Pagado
```

---

## 📤 Exportación de información

El proyecto permite exportar registros individuales a archivos de texto.

Los archivos utilizados son:

```text
clientes.txt
empleados.txt
prestamos.txt
pagos.txt
```

Los registros se almacenan utilizando `|` como separador.

Ejemplo:

```text
CLIENTE|1|Juan|123456|correo@email.com|3000000000|Puntual
```

Los archivos se crean automáticamente cuando se realiza una exportación.

La clase responsable de esta funcionalidad es:

```text
FileUtils
```

---

## Evidencias de ejecución

### Cambio de estado de prestamo a Pagado

![cambioDeEstadoDePrestamoAPagado](images/cambioDeEstadoDePrestamoAPagado.png)

### Archivo de clientes

![clientesFile](images/clientesFile.png)

### Clientes morosos

![clientesMorosos](images/clientesMorosos.png)

### Consulta de Empleados por Id

![consultaDeEmpleadosPorId](images/consultaDeEmpleadosPorId.png)

### Consulta de prestamos hecha

![consultaDePrestamosHechaSatisfactoriamente](images/consultaDePrestamosHechaSatisfactoriamente.png)

### Creacion de prestamo

![creacionDePrestamo](images/creacionDePrestamo.png)

### Definir clientes como morosos

![definirClienteComoMoroso](images/definirClienteComoMoroso.png)

### Ejemplo de abonos

![ejemploDeAbonos](images/ejemploDeAbonos.png)

### Archivo de empleados

![empleadosFile](images/empleadosFile.png)

### Ejemplos de exception en consulta de prestamos

![exceptionsConsultaDePrestamos](images/exceptionsConsultaDePrestamos.png)

### Exportacion de cliente

![exportacionDeCliente](images/exportacionDeCliente.png)

### Exportacion de pago

![exportacionDePago](images/exportacionDePago.png)

### Exportacion de prestamo

![exportacionDePrestamo](images/exportacionDePrestamo.png)

### Exportacion de empleado

![exportacionEmpleados](images/exportacionEmpleados.png)

### Historico de pagos

![historicoDePagos](images/historicoDePagos.png)

### Listar clientes

![listarClientes](images/listarClientes.png)

### Archivo de pagos

![pagosFile](images/pagosFile.png)

### Prestamos activos

![prestamosActivos](images/prestamosActivos.png)

### Archivo de prestamos

![prestamosFile](images/prestamosFile.png)

### Prestamos vencidos

![prestamosVencidos](images/prestamosVencidos.png)

### Registro de clientes

![registroDeCliente](images/registroDeCliente.png)

### Registro de empleados

![registroDeEmpleado](images/registroDeEmpleado.png)

### Saldo pendiente

![saldoPendiente](images/saldoPendiente.png)

### Simulacion de prestamo

![simulacionDePrestamo](images/simulacionDePrestamo.png)

## 🔐 Configuración

Antes de ejecutar el proyecto es necesario tener:

- Java instalado.
- MySQL instalado y ejecutándose.
- La base de datos `crediya_db`.
- El conector JDBC de MySQL configurado mediante Maven.
- Las tablas necesarias creadas.

La conexión se gestiona desde:

```text
com.apex.database.ConexionDB
```

### Importante

Las credenciales de la base de datos **no deberían publicarse en GitHub**.

Se recomienda utilizar variables de entorno o un archivo de configuración que no sea incluido en el repositorio.

Por ejemplo:

```text
DB_URL
DB_USER
DB_PASSWORD
```

Y agregar cualquier archivo local que contenga credenciales al `.gitignore`.

---

## ▶️ Ejecución

### 1. Clonar el repositorio

```bash
git clone <URL_DEL_REPOSITORIO>
```

### 2. Entrar al proyecto

```bash
cd CrediYA_S.A.S
```

### 3. Configurar MySQL

Crear la base de datos:

```sql
CREATE DATABASE crediya_db;
```

Crear las tablas correspondientes a:

```text
empleados
clientes
prestamos
pagos
```

y configurar correctamente sus claves primarias y foráneas.

### 4. Configurar las credenciales

Configurar la conexión de `ConexionDB` de acuerdo con las credenciales locales de MySQL.

### 5. Ejecutar el proyecto

Desde IntelliJ IDEA:

```text
Main.java → Run
```

O mediante Maven, dependiendo de la configuración del proyecto:

```bash
mvn clean install
```

---

## ☕ Conceptos de Java aplicados

Durante el desarrollo se aplicaron diferentes conceptos de Java.

### Programación Orientada a Objetos

Se utilizan clases para representar las entidades:

```java
Empleado
Cliente
Prestamo
Pago
Simulacion
```

### Encapsulamiento

Los atributos de los modelos son privados y se accede a ellos mediante getters y setters.

### Constructores

Se utilizan constructores vacíos y constructores parametrizados.

### Streams

Se utilizan Streams para filtrar colecciones:

```java
prestamos.stream()
        .filter(prestamo -> prestamo.getCliente_id() == clienteId)
        .toList();
```

También para realizar operaciones sobre pagos:

```java
pagos.stream()
        .mapToDouble(pago -> pago.getMonto())
        .sum();
```

### Manejo de excepciones

El proyecto utiliza:

```java
try
catch
```

para manejar errores relacionados principalmente con:

```text
SQLException
IllegalArgumentException
```

### JDBC

Las consultas utilizan:

```java
Connection
PreparedStatement
ResultSet
```

y `try-with-resources` para cerrar automáticamente los recursos.

### PreparedStatement

Las consultas SQL utilizan parámetros:

```java
WHERE id = ?
```

Esto permite separar los datos de la estructura de la consulta.

### Fechas

Para manejar fechas dentro de Java se utiliza:

```java
LocalDate
```

Mientras que JDBC permite convertirlas a:

```java
java.sql.Date
```

mediante:

```java
Date.valueOf(...)
```

### Archivos

La exportación utiliza:

```java
Files.writeString(...)
```

junto con:

```java
StandardOpenOption.CREATE
StandardOpenOption.APPEND
```

---

## 🧠 Aprendizajes del proyecto

Este proyecto integra diferentes áreas del desarrollo de aplicaciones:

```text
Java
  │
  ├── POO
  ├── Colecciones
  ├── Streams
  ├── Excepciones
  ├── Fechas
  ├── Archivos
  │
  └── JDBC
        │
        ▼
      MySQL
```

El objetivo principal es comprender cómo conectar una aplicación Java de consola con una base de datos relacional y organizar el código mediante diferentes responsabilidades.

---

## 🚀 Posibles mejoras futuras

Entre las mejoras que pueden incorporarse posteriormente se encuentran:

- Migrar las credenciales a variables de entorno.
- Mejorar la validación de datos ingresados.
- Separar algunas responsabilidades que actualmente se encuentran dentro de las Views.
- Optimizar algunas consultas para evitar cargar listas completas desde la base de datos.
- Incorporar más estados para los préstamos.
- Mejorar el cálculo financiero de las cuotas.
- Incorporar autenticación de usuarios.
- Agregar pruebas unitarias.
- Implementar una interfaz gráfica o una API REST.
- Mejorar el sistema de reportes.
- Incorporar paginación para consultas grandes.
- Crear documentación SQL completa.
- Incorporar Docker para facilitar la ejecución del proyecto.

---

## 👨‍💻 Autor

**Juan David Arias Patiño**

Proyecto académico desarrollado para practicar Java, JDBC, MySQL y desarrollo de aplicaciones de consola.

---

## 📄 Licencia

Proyecto desarrollado con fines educativos y de aprendizaje.
