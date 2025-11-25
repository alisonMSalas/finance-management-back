# Documentación con Doxygen

## Descripción

Este proyecto utiliza Doxygen para generar documentación automática del código fuente Java. La documentación incluye diagramas de clases, relaciones entre componentes y descripciones detalladas de cada elemento del código.

## Requisitos Previos

### Instalación de Doxygen

#### Windows
1. Descargar el instalador desde [doxygen.nl/download.html](https://www.doxygen.nl/download.html)
2. Ejecutar el instalador y seguir las instrucciones
3. Verificar la instalación:
   ```bash
   doxygen --version
   ```

#### Linux (Ubuntu/Debian)
```bash
sudo apt-get update
sudo apt-get install doxygen
```

#### macOS
```bash
brew install doxygen
```

## Generar la Documentación

### Paso 1: Navegar al directorio del proyecto
```bash
cd C:\Users\Rafa\Documents\Uni\7mo\das\2do_parcial\finance-management-back
```

### Paso 2: Ejecutar Doxygen
```bash
doxygen Doxyfile
```

Este comando generará la documentación en el directorio `docs/html`.

### Paso 3: Visualizar la documentación
Abrir el archivo principal en un navegador:
```bash
# Windows
start docs/html/index.html

# Linux
xdg-open docs/html/index.html

# macOS
open docs/html/index.html
```

## Estructura de la Documentación

Después de generar la documentación, encontrarás:

```
docs/
└── html/
    ├── index.html          # Página principal
    ├── annotated.html      # Lista de clases
    ├── classes.html        # Índice de clases
    ├── files.html          # Lista de archivos
    └── ...                 # Otros archivos generados
```

## Configuración del Doxyfile

El archivo `Doxyfile` contiene la configuración principal:

- **INPUT**: `src/main/java` - Directorio del código fuente
- **OUTPUT_DIRECTORY**: `docs` - Donde se guarda la documentación
- **RECURSIVE**: `YES` - Procesa subdirectorios
- **EXTRACT_ALL**: `YES` - Documenta todos los elementos
- **JAVADOC_AUTOBRIEF**: `YES` - Usa estilo JavaDoc

## Formato de Comentarios Doxygen

### Para clases:
```java
/**
 * @file ClassName.java
 * @brief Descripción breve de la clase
 *
 * Descripción detallada de la funcionalidad
 *
 * @author Nombre del autor
 * @version 1.0
 */
```

### Para métodos:
```java
/**
 * @brief Descripción del método
 * @param paramName Descripción del parámetro
 * @return Descripción del valor de retorno
 */
```

### Para atributos:
```java
/**
 * @brief Descripción del atributo
 */
private String campo;
```

## Actualizar la Documentación

Cada vez que modifiques el código:

1. Asegúrate de que los comentarios Doxygen estén actualizados
2. Ejecuta `doxygen Doxyfile` nuevamente
3. La documentación se regenerará automáticamente

## Ignorar Archivos

Para excluir archivos de la documentación, edita `Doxyfile`:
```
EXCLUDE_PATTERNS = */test/* */deprecated/*
```

## Problemas Comunes

### "doxygen: command not found"
- Verifica que Doxygen esté instalado correctamente
- Asegúrate de que esté en el PATH del sistema

### Documentación vacía o incompleta
- Verifica que `INPUT` apunte al directorio correcto
- Confirma que `RECURSIVE = YES` esté configurado
- Revisa los patrones de exclusión en `EXCLUDE_PATTERNS`

### Advertencias sobre documentación faltante
- Activa/desactiva con `WARN_IF_UNDOCUMENTED` en el Doxyfile
- Agrega comentarios Doxygen a los elementos sin documentar

## Recursos Adicionales

- [Documentación oficial de Doxygen](https://www.doxygen.nl/manual/)
- [Comandos especiales de Doxygen](https://www.doxygen.nl/manual/commands.html)
- [Ejemplos de documentación](https://www.doxygen.nl/manual/docblocks.html)
