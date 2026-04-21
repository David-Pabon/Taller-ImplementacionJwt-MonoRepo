# Taller de Autenticación JWT y Docker

## Datos del Estudiante

- **Nombre completo:** David Pabon Mejia
- **Variables #14:**
  - Puerto de Hash: `HashEngine`
  - Base Path: `/api/verify`
  - Claim JWT: `"userGrant"`
  - Propiedad YAML: `token.secret.key` / `token.secret.ttl-ms`

## Requisitos Previos

- Docker Desktop instalado
- Puerto 6001 disponible
- Java 21+ (para compilar localmente)

- # Comandos cURL para Pruebas de API

---

## 1. Registro de usuario (`ORGANIZER`)

```bash
curl -X POST http://localhost:6001/api/verify/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Carlos Organizer",
    "email": "carlos@eventos.com",
    "password": "organizer123",
    "role": "ORGANIZER"
  }'
```

---

## 2. Login (obtener token)

```bash
curl -X POST http://localhost:6001/api/verify/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "carlos@eventos.com",
    "password": "organizer123"
  }'
```

---

## 3. Acceso con token — Crear evento (solo `ORGANIZER`)

```bash
curl -X POST http://localhost:6001/api/events \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN_OBTENIDO_EN_LOGIN>" \
  -d '{
    "name": "Rock Festival 2026",
    "description": "El mejor festival de rock",
    "venue": "Parque Simón Bolívar",
    "eventDate": "2026-12-15T20:00:00",
    "saleStartDate": "2026-06-01T00:00:00",
    "saleEndDate": "2026-12-14T23:59:59",
    "category": "MUSICAL",
    "maxTicketsPerUser": 6,
    "totalTickets": 5000,
    "organizerId": "ID_DEL_ORGANIZER"
  }'
```

---

## 4. Acceso denegado (`403`) — `BUYER` intenta crear evento

### 4.1 Registrar usuario `BUYER`

```bash
curl -X POST http://localhost:6001/api/verify/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Ana Compradora",
    "email": "ana@comprador.com",
    "password": "buyer123",
    "role": "BUYER"
  }'
```

### 4.2 Login como `BUYER`

```bash
curl -X POST http://localhost:6001/api/verify/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "ana@comprador.com",
    "password": "buyer123"
  }'
```

### 4.3 Intentar crear evento — Debe retornar `403 Forbidden`

```bash
curl -X POST http://localhost:6001/api/events \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN_DEL_BUYER>" \
  -d '{
    "name": "Intento Fallido",
    "venue": "Lugar Cualquiera",
    "totalTickets": 100,
    "organizerId": "uuid-cualquiera"
  }'
```

## 5. Registrar `ADMIN`

```bash
curl -X POST http://localhost:6001/api/verify/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Laura Admin",
    "email": "laura@admin.com",
    "password": "admin123",
    "role": "ADMIN"
  }'
```

---

## 5.1 Login `ADMIN` (obtener token)

```bash
curl -X POST http://localhost:6001/api/verify/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "laura@admin.com",
    "password": "admin123"
  }'
```

---

## 5.2 Acceso denegado (`403`) — `ADMIN` intenta crear evento

```bash
curl -X POST http://localhost:6001/api/events \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN_ADMIN>" \
  -d '{
    "name": "Evento Admin",
    "description": "Admin intentando crear evento",
    "venue": "Teatro Nacional",
    "eventDate": "2026-12-15T20:00:00",
    "saleStartDate": "2026-06-01T00:00:00",
    "saleEndDate": "2026-12-14T23:59:59",
    "category": "CULTURAL",
    "maxTicketsPerUser": 10,
    "totalTickets": 200,
    "organizerId": "<ID_DEL_ADMIN>"
  }'
```

## Ejecutar el proyecto con Docker

```bash
# Construir y levantar los contenedores
docker-compose up -d

# Ver logs de la aplicación
docker-compose logs -f app

# Detener los contenedores
docker-compose down
