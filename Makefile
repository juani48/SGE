# Variables globales
COMPOSE = docker compose
DOCKER = docker
MAVEN = mvn


# Ayuda: muestra todos los comandos disponibles (ejecuta por defecto al escribir 'make')
.PHONY: help
help:
	@echo "Comandos disponibles para el proyecto:"
	@echo "  make build-app    - Compila el JAR de Spring Boot con Maven"
	@echo "  make docker-build - Construye la imagen Docker de la app"
	@echo "  make up           - Levanta todos los contenedores (app, db, adminer) en segundo plano"
	@echo "  make up-dev       - Compila, construye y levanta todo en modo desarrollo"
	@echo "  make down         - Detiene y elimina los contenedores (mantiene los datos de la BD)"
	@echo "  make down-clean   - Detiene todo y ELIMINA el volumen de la BD (reinicio total)"
	@echo "  make restart      - Reinicia los contenedores (útil tras cambios de código)"

.PHONY: build-app
build-app:
	$(MAVEN) clean package -DskipTests

.PHONY: docker-build
docker-build:
	$(COMPOSE) build

.PHONY: up
up:
	$(COMPOSE) up -d --build
	@echo "Aplicación disponible en http://localhost:8080"
	@echo "Adminer disponible en http://localhost:8081"

.PHONY: up-dev
up-dev: 
	$(COMPOSE) up --build
	@echo "Sistema completo levantado y listo para desarrollo."

.PHONY: down
down:
	$(COMPOSE) down

.PHONY: clean
clean:
	$(COMPOSE) down -v
	@echo "Volumen de PostgreSQL eliminado. Los datos se han perdido."

.PHONY: restart
restart: down up
