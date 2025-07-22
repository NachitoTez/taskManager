build:
	cd backend && ./mvnw clean install
	cd frontend && npm install

run-backend:
	cd backend && ./mvnw spring-boot:run

run-frontend:
	cd frontend && npm run dev