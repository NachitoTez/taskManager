run-backend:
	cd backend && ./mvnw clean install && ./mvnw spring-boot:run

run-frontend:
	cd frontend && npm install && npm run dev