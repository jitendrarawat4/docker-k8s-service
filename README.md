# docker-k8s

employee, departmnet with Junit and devops

docker exec -it mysql-db mysql -u employee_user -p

####To build new docker image of my service employee
docker build -t employee-service:0.0.1 .

#### to add tag

Docker images
docker tag <IMAGE_ID> jitendra111/employee-service:0.0.1

####to push image to docker hub
docker push jitendra111/employee-service:0.0.1

####to start and stop containers in docker desktop
docker-compose down
docker-compose up

---

### Kubernetes

minikube start

###to view k8s dashboard on web
minikube dashboard

kubectl apply -f mysql-stateful-set.yml
kubectl apply -f employee-deployment.yml

###To get pods
kubectl get pod

### To expose IP outside Kubernetes cluster

kubectl expose pod employee-service-54d55c6bc7-zjckc --type=NodePort --name=employee-service --port=8080 --target-port=8080

kubectl get svc employee-service

minikube service employee-lb

## kubectl logs -f <pod-name>

docker build -t order-creation-service:latest .
docker build -t order-execution-service:latest .

docker-compose down
docker-compose up

docker-compose up -d --build
