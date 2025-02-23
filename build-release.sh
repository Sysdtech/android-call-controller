#!/bin/bash

# Asegurar que el script falle si hay algún error
set -e

# Configurar variables de entorno
export JAVA_HOME=/nix/store/2vwkssqpzykk37r996cafq7x63imf4sp-openjdk-21+35
export ANDROID_HOME=/opt/android-sdk
export PATH=$PATH:$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools

# Mostrar variables de entorno para debug
echo "JAVA_HOME: $JAVA_HOME"
echo "ANDROID_HOME: $ANDROID_HOME"
echo "PATH: $PATH"
echo "Contenido de local.properties:"
cat local.properties

# Limpiar builds anteriores
./gradlew clean

# Construir APK release
./gradlew :app:assembleRelease

# Crear directorio prebuilt si no existe
mkdir -p ../prebuilt/android

# Copiar APK al directorio prebuilt
cp app/build/outputs/apk/release/app-release.apk ../prebuilt/android/

echo "APK compilado y copiado exitosamente a prebuilt/android/app-release.apk"