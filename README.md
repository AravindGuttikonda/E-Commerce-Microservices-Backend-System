Designed and developed a scalable, event-driven microservices architecture using Spring Boot, Kafka, and Keycloak for authentication. Implemented efficient service communication with Eureka, Spring Cloud Gateway, and Redis for caching.

Key Microservices:

API Gateway – Acts as the single entry point for all the requests, manages authentication and authorization via Keycloak.

User Service – Handles user management and emits Kafka events for notifications.

Product Service – Manages products and their categories, user Redis for caching frequent requests

Order Service – Manages orders, interacts with User and Product Services via RestTempate to get details, and triggers notifications.

Payment Service – Processes payments via Razorpay and sends notifications post-payment.

Notification Service – Listens to Kafka topics and sends transactional emails to users.
