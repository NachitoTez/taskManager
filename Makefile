up: build jar
	docker compose up --build --remove-orphans

run-frontend:
	cd frontend && npm install && npm run dev


#Local:
build:
	cd backend && ./mvnw clean install
	cd frontend && npm install

run-backend:
	cd backend && ./mvnw spring-boot:run


jar:
	cd backend && ./mvnw clean package -DskipTests && \
	jar_name=$$(ls target/taskmanager-*.jar | grep -v original) && \
	cp "$$jar_name" target/taskmanager-backend.jar


# Solo levanta (asumiendo que ya está build)
run:
	docker compose up

# Baja los contenedores
down:
	docker compose down

# Baja los contenedores y elimina también los volúmenes persistentes
purge:
	docker compose down -v

