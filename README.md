# Base de Datos Empleados con Docker

Este README te guiará para crear una base de datos PostgreSQL en Docker con la tabla `empleado`.

## Instalación en Windows

### 1. Instalar Docker Desktop

1. Descarga Docker Desktop desde: https://www.docker.com/products/docker-desktop/
2. Ejecuta el instalador como administrador
3. Sigue las instrucciones del asistente de instalación
4. Reinicia tu computadora cuando se te solicite
5. Inicia Docker Desktop desde el menú de inicio

### 2. Verificar la instalación

Abre PowerShell o Command Prompt y ejecuta:

```bash
docker --version
docker-compose --version
```

Deberías ver las versiones instaladas de Docker y Docker Compose.

### 3. Configurar WSL2 (si es necesario)

Si Docker te solicita habilitar WSL2:

1. Abre PowerShell como administrador
2. Ejecuta: `wsl --install`
3. Reinicia tu computadora
4. Configura un usuario y contraseña para Ubuntu cuando se abra

## Instrucciones de Uso

### 1. Iniciar la Base de Datos

En el directorio donde tienes los archivos `docker-compose.yml` e `init.sql`:

```bash
docker-compose up -d
```

### 2. Verificar que el Contenedor Esté Ejecutándose

```bash
docker ps
```

### 3. Conectarse a la Base de Datos

```bash
# Usando psql desde el contenedor
docker exec -it empleados-db psql -U marlenejb -d empleados

# O usando un cliente externo como pgAdmin, DBeaver, etc.
# Host: localhost, Puerto: 5432, Usuario: marlenejb, BD: empleados
```

### 4. Verificar la Tabla

Una vez conectado a PostgreSQL:

```sql
-- Listar tablas
\dt

-- Describir la estructura de la tabla empleado
\d empleado

-- Consultar datos
SELECT * FROM empleado;
```

## Parámetros de Conexión

- **Host:** localhost
- **Puerto:** 5432
- **Base de datos:** empleados
- **Usuario:** marlenejb
- **Contraseña:** kimi no na wa

## Comandos Útiles

```bash
# Ver logs del contenedor
docker logs empleados-db

# Detener todos los servicios
docker-compose down

# Detener y eliminar volúmenes (¡cuidado! se perderán los datos)
docker-compose down -v

# Reiniciar los servicios
docker-compose restart

# Ver el estado de los contenedores
docker-compose ps
```

## Mapeo POJO a Base de Datos

| Campo POJO | Tipo Java | Campo DB | Tipo PostgreSQL |
|------------|-----------|----------|-----------------|
| clave      | long      | clave    | BIGSERIAL (PK)  |
| nombre     | String    | nombre   | VARCHAR(255)    |
| direccion  | String    | direccion| VARCHAR(500)    |
| telefono   | String    | telefono | VARCHAR(20)     |

## Clientes Recomendados para Windows

### Herramientas GUI:
- **pgAdmin 4**: Cliente web oficial de PostgreSQL
- **DBeaver**: Cliente universal gratuito
- **DataGrip**: IDE de JetBrains (de pago)

### Herramientas de línea de comandos:
- **psql**: Incluido en PostgreSQL client
- **Windows Terminal**: Para mejor experiencia en línea de comandos

## Troubleshooting en Windows

**Docker Desktop no inicia:**
- Verifica que la virtualización esté habilitada en BIOS
- Asegúrate de tener WSL2 instalado
- Ejecuta Docker Desktop como administrador

**Puerto 5432 en uso:**
Si ya tienes PostgreSQL instalado localmente, cambia el puerto:
```yaml
ports:
  - "5433:5432"  # Usar puerto 5433 externamente
```

**Problemas de permisos:**
- Ejecuta PowerShell/CMD como administrador
- Verifica que tu usuario esté en el grupo "docker-users"

**WSL2 consume mucha RAM:**
Crea un archivo `.wslconfig` en `C:\Users\<tu-usuario>\`:
```ini
[wsl2]
memory=4GB
processors=2
```

## Notas Importantes

- Docker Desktop debe estar ejecutándose para usar los contenedores
- Los datos se persistirán en un volumen de Docker para evitar pérdida de información
- La contraseña contiene espacios, por lo que debe ir entre comillas en conexiones manuales
- El campo `clave` se configuró como `BIGSERIAL` para auto-incremento