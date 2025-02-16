E-Commerce-Microservices-Backend-System

Designed and developed a scalable, event-driven microservices architecture using Spring Boot, Kafka, and Keycloak for authentication. Implemented efficient service communication with Eureka, Spring Cloud Gateway, and Redis for caching.

Service Discovery (Eureka): Centralized service registry enabling dynamic service discovery and load balancing.

API Gateway: Acts as the entry point for all microservices, handling authentication using Keycloak as the authorization server and API Gateway as the resource server.

User Service: Manages user data and publishes Kafka events to Notification Service upon user registration.

Product Service: Handles product management with Redis caching, pagination, and sorting for optimized performance.

Order Service: Processes orders by communicating with User and Product Services using RestTemplate and publishes Kafka events to Notification Service upon order creation.

Payment Service: Manages payments via Razorpay integration and publishes Kafka events to Notification Service after successful payment.

Notification Service: Listens to Kafka topics and sends email notifications for user registration, order confirmation, and payment status.
