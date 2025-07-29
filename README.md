# 🌍 Geo Report API

API REST para reportar eventos ciudadanos con geolocalización. Los usuarios pueden registrarse, confirmar su correo electrónico, iniciar sesión y reportar eventos como robos, asaltos o vandalismo con coordenadas geográficas para advertir a otros ciudadanos.

## 🚀 Características

- Registro de usuarios con confirmación por correo electrónico
- Autenticación y autorización con JWT
- Publicación de reportes ciudadanos con ubicación geográfica
- Base de datos espacial usando PostgreSQL + PostGIS
- Envío de notificaciones por correo
- Contenedor Docker listo para producción

## 🧰 Tecnologías

- Java 17
- Spring Boot 3.x
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL + PostGIS
- Spring Mail
- Docker / Docker Compose
- Swagger / OpenAPI

## ⚙️ Configuración

### Variables de entorno (Docker)

Estas variables deben estar definidas en tu archivo `docker-compose.yml`:

```env
DATABASE_URL=jdbc:postgresql://<host>:<port>/<database_name>
DATABASE_USERNAME=<tu_usuario_db>
DATABASE_PASSWORD=<tu_contraseña_db>
MAIL_USERNAME=<tu_correo>
MAIL_PASSWORD=<tu_contraseña_correo_app>
JWT_SECRET_KEY=clave_super_secreta_para_firmar_jwt

## 🐳 Uso con Docker
1. Clona el repositorio

git clone https://github.com/tu-usuario/geo-report-api.git
cd geo-report-api

2. Ejecuta con Docker Compose

docker-compose up --build

La API quedará disponible en:
📍 http://localhost:8080

La base de datos PostGIS en:
📍 localhost:5433

## 📬 Endpoints principales
| Método | Endpoint                | Descripción                              |
| ------ | ----------------------- | ---------------------------------------- |
| POST   | `/api/v1/auth/register` | Registro de usuarios                     |
| POST   | `/api/v1/auth/login`    | Login y generación de token JWT          |
| POST   | `/api/v1/reports`       | Publicar reporte ciudadano               |
| GET    | `/api/v1/reports`       | Listar reportes con ubicación            |

## 🧪 Swagger
Accede a la documentación interactiva en:
📄 http://localhost:8080/swagger-ui/index.html

## 🔐 Roles del sistema
| Rol          | Descripción                              |
| ------------ | ---------------------------------------- |
| `ROLE_USER`  | Usuario registrado, puede crear reportes |
| `ROLE_ADMIN` | Puede gestionar todos los reportes       |


🧑‍💻 Autor
Kevin Antonio Ortega Parra
Desarrollador Backend Java
📫 kevin.ortegaap@est.iudigital.edu.co

Este proyecto fue desarrollado como parte de mi portafolio personal. ¡Contribuciones y feedback son bienvenidos!
