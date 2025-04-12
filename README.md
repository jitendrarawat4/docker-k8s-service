# docker-k8s-Microservice

This project demonstrates a microservices-based architecture using Spring Boot, comprising two core services:

Employee Service: Manages employee information and communicates with the Department Service to fetch department details.

Department Service: Manages department-related data.

🔧 Key Features & Technologies
Spring Boot for microservice development.

REST APIs for inter-service communication using:

RestTemplate

WebClient

JUnit for unit and integration testing.

Docker for containerizing microservices.

Kubernetes for orchestration and service discovery.

DevOps Workflow: Supports deployment and scaling in a containerized environment.

### DOCKER

---

docker build -t order-creation-service:latest .
docker build -t order-execution-service:latest .

docker-compose down (To stop and remove docker containers)
docker-compose up (To start docker containers)

docker-compose up -d --build

---

docker exec -it kafka kafka-topics --create --topic order-topic --partitions 1 --replication-factor 1 --bootstrap-server localhost:9092

docker exec -it kafka kafka-topics --create --topic order-topic --partitions 1 --replication-factor 1 --bootstrap-server kafka:9092

kafka-topics.sh --describe --topic order-topic --bootstrap-server localhost:9092
kafka-topics.sh --alter --topic order-topic --partitions 5 --bootstrap-server localhost:9092

---

### ZIPKIN

---

docker run -d -p 9411:9411 openzipkin/zipkin (To start Zipkin on docker)

## http://localhost:9411

### Maven + Mysql

---

mvn clean package -DskipTests

docker exec -it mysql bash

mysql -u root -p

To exit - \q. -> ctrl+D

## kubectl exec -it mysql-0 -- mysql -u root -p

### DOCKER

---

docker build -t employee-service:0.0.1 . (To build new docker image without tag)
docker build -t jitendra111/employee-service:0.0.1 . (To build new docker image without tag)

docker tag <IMAGE_ID> jitendra111/employee-service:0.0.1 (To add tag separately)

## docker push jitendra111/employee-service:0.0.1 (To push docker image to docker registry)

### KUBERNETES

---

minikube start
minikube stop

minikube dashboard (To start dashboard)

kubectl apply -f <file-name/folder-name> (To apply compose files and hit from file/folder root path)

kubectl apply -f k8s
kubectl delete -f k8s

kubectl get pod (To get running Pods)

kubectl logs -f <pod-name> (To check runnings logs)

##

minikube service list (To expose service URL through lb)
minikube service <service-name>

### To expose IP outside Kubernetes cluster

kubectl expose pod <iamge-id> --type=NodePort --name=employee-service --port=8080 --target-port=8080

kubectl port-forward <pod name> 8080:8080

minikube image ls --format="table"
minikube image rm <iamge-name>

---

##

To encode values
echo -n '<value>' | base64

---
