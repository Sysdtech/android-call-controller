# Guía Completa de Compilación y Configuración

## 1. Compilación del APK

### Opción Recomendada: GitHub Actions (Automático)

1. Acceder al repositorio en GitHub:
   - Ir a la pestaña "Actions"
   - Buscar "Build Android APK"
   - Clic en "Run workflow"
   - Seleccionar rama "main"
   - Esperar compilación (5-10 minutos)
   - Descargar desde "Artifacts" > "app-release"

### Configuración del Keystore (Ya implementado)

```bash
Ubicación: android/app/keystore.jks
Contraseña del keystore: "android"
Alias de la clave: "key0"
Contraseña de la clave: "android"
Validez: 10000 días
```

## 2. Requisitos del Sistema

### Dispositivo Android
- Versión: Android 7.0 (API 24) o superior
- Espacio libre: Mínimo 10MB
- Conexión a internet estable
- SIM con saldo disponible

### Permisos Requeridos
- CALL_PHONE (Realizar llamadas)
- READ_PHONE_STATE (Estado del teléfono)
- READ_PHONE_NUMBERS (Número de teléfono)
- READ_CALL_LOG (Registro de llamadas)
- INTERNET (Conexión al servidor)

## 3. Instalación en el Dispositivo

1. Preparación:
   ```
   Configuración > Seguridad > Orígenes desconocidos > Activar
   ```

2. Instalación:
   - Abrir el APK descargado
   - Aceptar la instalación
   - Conceder TODOS los permisos solicitados

3. Verificación:
   - Abrir la aplicación
   - Confirmar que muestra el número de teléfono
   - Verificar que muestra el operador
   - Comprobar conexión al servidor

## 4. Configuración en el Panel Web

### Datos Necesarios (Todos Obligatorios)

1. ID del Dispositivo:
   - Se genera automáticamente
   - Formato UUID (ejemplo: 550e8400-e29b-41d4-a716-446655440000)

2. Nombre del Dispositivo:
   - Elegir un nombre descriptivo
   - Ejemplo: "Samsung S21 - Ventas"
   - Debe ser único en el sistema

3. Número de Teléfono:
   - Formato internacional obligatorio
   - Ejemplo: +34612345678
   - Sin espacios ni caracteres especiales

4. Configuración del SIM:
   - Slot: 1 (Principal) o 2 (Secundario)
   - Operador: Nombre exacto del operador
   - Ejemplo: "Movistar", "Vodafone", "Orange"

### Verificación de Funcionamiento

1. Estado del Dispositivo:
   - Indicador verde = Online
   - Indicador rojo = Offline
   - Última conexión actualizada

2. Prueba de Llamada:
   - Introducir número en formato internacional
   - Verificar que el dispositivo realiza la llamada
   - Confirmar registro en el panel

## 5. Solución de Problemas

### Problemas de Conexión

1. Dispositivo Offline:
   - Verificar conexión a internet
   - Comprobar permisos de la app
   - Reiniciar aplicación
   - Verificar URL del servidor

2. Errores en Llamadas:
   - Verificar formato del número (+34...)
   - Comprobar saldo del SIM
   - Confirmar permisos de llamada
   - Verificar estado "online"

### Contacto de Soporte

Para problemas técnicos o consultas:
- Abrir un issue en GitHub
- Describir el problema detalladamente
- Incluir logs si es posible

## 6. Integración con el Panel Web

- El APK compilado se sirve en: `/download/app.apk`
- Accesible desde el botón "Descargar App Android"
- Se actualiza automáticamente tras cada compilación

---

Para consultas adicionales o problemas específicos, por favor crear un issue en el repositorio del proyecto.