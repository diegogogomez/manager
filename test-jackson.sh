#!/bin/bash

echo "🧪 Probando configuración de Jackson con Instant..."

BASE_URL="http://localhost:8080"

# 1. Verificar que la aplicación esté corriendo
echo "1️⃣ Verificando que la aplicación esté corriendo..."
RESPONSE=$(curl -s -X GET "$BASE_URL/api/messages/health")

if [[ $RESPONSE != *"running"* ]]; then
    echo "❌ Error: La aplicación no está corriendo"
    echo "Ejecuta: ./gradlew bootRun"
    exit 1
fi

echo "✅ Aplicación corriendo correctamente"
echo ""

# 2. Enviar mensaje para probar serialización
echo "2️⃣ Enviando mensaje para probar serialización de Instant..."
RESPONSE=$(curl -s -X POST "$BASE_URL/api/messages/send" \
    -H "Content-Type: application/json" \
    -d '{"message": "Prueba de Jackson con Instant"}')

if [[ $RESPONSE == *"exitosamente"* ]]; then
    echo "✅ Mensaje enviado correctamente"
else
    echo "❌ Error enviando mensaje: $RESPONSE"
    exit 1
fi

echo ""

# 3. Esperar procesamiento
echo "3️⃣ Esperando procesamiento..."
sleep 2

# 4. Verificar que se guardó en Elasticsearch
echo "4️⃣ Verificando que se guardó en Elasticsearch..."
RESPONSE=$(curl -s -X GET "$BASE_URL/api/elasticsearch/messages")

if [[ $RESPONSE == *"Prueba de Jackson con Instant"* ]]; then
    echo "✅ Mensaje encontrado en Elasticsearch"
    echo "✅ Jackson está funcionando correctamente con Instant"
else
    echo "❌ Mensaje no encontrado en Elasticsearch"
    echo "❌ Posible problema con Jackson/Instant"
    exit 1
fi

echo ""

# 5. Mostrar el mensaje completo
echo "5️⃣ Mostrando mensaje completo:"
echo "$RESPONSE" | jq '.' 2>/dev/null || echo "$RESPONSE"

echo ""
echo "🎉 Prueba de Jackson con Instant completada exitosamente!"
echo "✅ La configuración está funcionando correctamente"

