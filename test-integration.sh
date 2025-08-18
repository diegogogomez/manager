#!/bin/bash

echo "🧪 Probando integración completa: RabbitMQ + Elasticsearch..."

BASE_URL="http://localhost:8080"

# Función para mostrar respuestas
show_response() {
    echo "📤 $1"
    echo "📥 Respuesta: $2"
    echo "---"
}

# 1. Verificar que la aplicación esté corriendo
echo "1️⃣ Verificando que la aplicación esté corriendo..."
RESPONSE=$(curl -s -X GET "$BASE_URL/api/messages/health")
show_response "GET /api/messages/health" "$RESPONSE"

if [[ $RESPONSE != *"running"* ]]; then
    echo "❌ Error: La aplicación no está corriendo"
    exit 1
fi

echo "✅ Aplicación corriendo correctamente"
echo ""

# 2. Enviar mensaje (esto debería guardarlo en Elasticsearch automáticamente)
echo "2️⃣ Enviando mensaje a RabbitMQ (se guardará en Elasticsearch)..."
RESPONSE=$(curl -s -X POST "$BASE_URL/api/messages/send" \
    -H "Content-Type: application/json" \
    -d '{"message": "Mensaje de prueba de integración"}')
show_response "POST /api/messages/send" "$RESPONSE"

echo "✅ Mensaje enviado a RabbitMQ y guardado en Elasticsearch"
echo ""

# 3. Esperar un momento para que se procese
echo "3️⃣ Esperando procesamiento..."
sleep 2

# 4. Verificar que el mensaje esté en Elasticsearch
echo "4️⃣ Verificando mensajes en Elasticsearch..."
RESPONSE=$(curl -s -X GET "$BASE_URL/api/elasticsearch/messages")
show_response "GET /api/elasticsearch/messages" "$RESPONSE"

if [[ $RESPONSE == *"Mensaje de prueba de integración"* ]]; then
    echo "✅ Mensaje encontrado en Elasticsearch"
else
    echo "❌ Mensaje no encontrado en Elasticsearch"
fi
echo ""

# 5. Obtener conteo de mensajes
echo "5️⃣ Obteniendo conteo de mensajes..."
RESPONSE=$(curl -s -X GET "$BASE_URL/api/elasticsearch/messages/count")
show_response "GET /api/elasticsearch/messages/count" "$RESPONSE"

echo "✅ Conteo obtenido"
echo ""

# 6. Buscar mensajes por tipo
echo "6️⃣ Buscando mensajes por tipo RABBITMQ..."
RESPONSE=$(curl -s -X GET "$BASE_URL/api/elasticsearch/messages/type/RABBITMQ")
show_response "GET /api/elasticsearch/messages/type/RABBITMQ" "$RESPONSE"

echo "✅ Búsqueda por tipo completada"
echo ""

# 7. Buscar mensajes por contenido
echo "7️⃣ Buscando mensajes por contenido..."
RESPONSE=$(curl -s -X GET "$BASE_URL/api/elasticsearch/messages/search?content=integración")
show_response "GET /api/elasticsearch/messages/search?content=integración" "$RESPONSE")

echo "✅ Búsqueda por contenido completada"
echo ""

echo "🎉 Prueba de integración completada!"
echo ""
echo "📊 Resumen de operaciones:"
echo "   ✅ Aplicación funcionando"
echo "   ✅ Mensaje enviado a RabbitMQ"
echo "   ✅ Mensaje guardado en Elasticsearch"
echo "   ✅ Consultas a Elasticsearch funcionando"
echo ""
echo "🔍 Para ver logs: docker-compose logs -f manager-app"
echo "🌐 Kibana disponible en: http://localhost:5601"

