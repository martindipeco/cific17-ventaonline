# CIFIC 17 Carrito de Compras
## Descripción
Carrito de Compras es una aplicación de gestión de ventas desarrollada en Java 17 con interfaz gráfica Swing y base de datos MySQL. La aplicación ofrece una experiencia completa de compra "en línea" con las siguientes características principales:

[Link a video - demo parte 1 (12 min)](https://youtube.com/live/9LjV5dSLbIM?feature=share)   
[Link a video - demo parte 2 (5 min)](https://youtube.com/live/wI8q06fJl98?feature=share)

- Menú de inicio de sesión con opciones de:
  - Iniciar sesión
  - Registrarse
  - Continuar como invitado
- Navegación y búsqueda de productos
- Carrito de compras con funcionalidades completas
- Generación de comprobante de compra en PDF
- Historial de pedidos por usuario

## Funcionalidades
1. **Autenticación de usuarios**
   - Registro de nuevos usuarios
   - Inicio de sesión
   - Opción de invitado
2. **Búsqueda y filtrado de productos**
   - Búsqueda por código de producto
   - Filtro por rango de precio
   - Búsqueda por nombre de producto
   - Filtro por categoría
3. **Gestión de carrito de compras**
   - Agregar productos
   - Eliminar productos
   - Mantener carrito al registrarse
4. **Proceso de compra**
   - Confirmación de compra
   - Generación de comprobante PDF
5. **Historial de pedidos**

## Repositorios Relacionados
- **Base de Datos**: [cific17-ventaonline-db](https://github.com/martindipeco/cific17-ventaonline-db)
- **Drivers**: [cific17-ventaonline-drivers](https://github.com/martindipeco/cific17-ventaonline-drivers)

## Requisitos
- Java 17
- MySQL
- Drivers JDBC (provistos en el repositorio de drivers)

## Estructura del Proyecto
- **Interfaz de Usuario**: Java Swing
- **Persistencia de Datos**: MySQL
- **Desarrollo**: Implementación manual (sin frameworks)

## Configuración
1. **Clonar los repositorios:**
```bash
git clone https://github.com/martindipeco/cific17-ventaonline-db
git clone https://github.com/martindipeco/cific17-ventaonline-drivers
git clone <url-de-tu-repositorio-principal>
```

2. **Configurar base de datos**
- Importar el script SQL del repositorio de base de datos
- Configurar conexión en la aplicación

3. **Instalar drivers**
- Añadir los drivers del repositorio de drivers al classpath

4. **Compilar y ejecutar**
```bash
# Comandos para compilar y ejecutar (ajustar según tu configuración)
javac [archivos-fuente]
java [clase-principal]
```

## Uso
1. Iniciar la aplicación
2. Elegir entre:
   - Iniciar sesión
   - Registrarse
   - Continuar como invitado
3. Navegar por el catálogo de productos
4. Realizar búsquedas y filtrados
5. Agregar productos al carrito
6. Confirmar compra
7. Generar comprobante PDF

### Ejemplo de Búsqueda
- Buscar por código de producto
- Filtrar por rango de precio
- Filtrar por categoría

## Contribuciones
¡Las contribuciones son bienvenidas! Por favor sigue estos pasos:

1. Haz un fork del proyecto
2. Crea una nueva rama:
```bash
git checkout -b feature/nueva-funcionalidad
```
3. Realiza tus cambios y haz commit:
```bash
git commit -m 'Agregar nueva funcionalidad'
```
4. Empuja tus cambios:
```bash
git push origin feature/nueva-funcionalidad
```
5. Abre un Pull Request

## Licencia
[Libre de uso]

## Contacto
[martindipeco@gmail.com]

## Notas Adicionales
- La aplicación mantiene el carrito de compras incluso al registrarse
- Se genera un comprobante PDF después de cada compra
- Cada usuario tiene acceso a su historial de pedidos
