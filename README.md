# Cloud-Native Microservices Reliability Platform

A production-grade distributed system built with Java and Spring Cloud, 
simulating enterprise control-plane behavior — service discovery, API routing, 
async messaging, and fault-tolerant inter-service communication.

---

## Architecture Overview
```
                        ┌─────────────────┐
                        │   API Gateway   │  ← Single entry point, routing + auth
                        └────────┬────────┘
                                 │
              ┌──────────────────┼──────────────────┐
              │                  │                  │
   ┌──────────▼──────┐  ┌───────▼────────┐  ┌──────▼──────────────┐
   │ Employee Service│  │ Dept. Service  │  │ Organization Service│
   └──────────┬──────┘  └───────┬────────┘  └──────┬──────────────┘
              │                  │                  │
              └──────────────────▼──────────────────┘
                                 │
                    ┌────────────▼────────────┐
                    │   Message / Event Bus   │  ← Async communication
                    └─────────────────────────┘

   ┌─────────────────────┐      ┌──────────────────────┐
   │   Service Registry  │      │    Config Server     │
   │   (Eureka)          │      │  (Centralized config)│
   └─────────────────────┘      └──────────────────────┘
```

---

## Services

| Service | Responsibility |
|---|---|
| `api-gateway` | Single entry point — routing, load balancing, JWT auth |
| `service-registry` | Eureka-based service discovery and registration |
| `config-server` | Centralized configuration management |
| `employee-service` | Employee domain CRUD + business logic |
| `department-service` | Department management, inter-service calls |
| `organization-service` | Organization-level operations |
| `message` | Async event-driven communication between services |
| `docker-compose` | Full stack local orchestration |

---

## Key Features

**Resilience Patterns**
- Circuit breakers using Resilience4j — prevents cascade failures
- Exponential backoff retry logic on downstream service calls
- Rate limiting at API Gateway level
- Idempotent API design for safe retries

**Service Communication**
- Synchronous: REST via Spring Cloud OpenFeign with load balancing
- Asynchronous: Event-driven messaging for decoupled service communication

**Security**
- OAuth2 / JWT based authentication at the gateway layer
- Role-based access control (RBAC) across services

***Observability**
- Prometheus metrics scraping per service
- Grafana dashboards for latency, throughput, error rates
- Zipkin distributed tracing for end-to-end request tracking
- Centralized logging across all services

**Infrastructure**
- Docker containerization for every service
- Docker Compose for full local stack orchestration
- Kubernetes-ready deployment configs (health checks, rolling updates)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot, Spring Cloud |
| Service Discovery | Netflix Eureka |
| API Gateway | Spring Cloud Gateway |
| Resilience | Resilience4j |
| Messaging | Kafka |
| Security | Spring Security, OAuth2, JWT |
| Monitoring | Prometheus, Grafana |
| Containerization | Docker, Kubernetes, Helm |
| Build | Maven |

---

## Running Locally

**Prerequisites:** Java 17+, Docker, Docker Compose
```bash
# Clone the repo
git clone https://github.com/tarunmarisetti/cloud-native-microservices
cd cloud-native-microservices

# Start all services
cd docker-compose
docker-compose up --build
```

**Services will be available at:**
- API Gateway: `http://localhost:8080`
- Service Registry (Eureka UI): `http://localhost:8761`
- Grafana Dashboard: `http://localhost:3000`

---

## What I Learned / Applied

- Strangler Fig migration pattern (applied professionally at Infosys/AT&T)
- Production resilience patterns: circuit breakers, retries, bulkheads
- Event-driven architecture with async messaging
- Container orchestration and health-check configuration
- Centralized config management across microservices

---

## Author

**Tarun Sai Marisetti**  
Backend Engineer | Java • Spring Boot • Microservices  
[LinkedIn](https://linkedin.com/in/tarun-marisetti) · 
[Portfolio](https://tarunsaimarisetti.vercel.app)
