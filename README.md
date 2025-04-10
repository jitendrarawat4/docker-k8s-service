# docker-k8s-Microservice

employee, department with Junit and devops toold like docker, kubernetes

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

#######
To encode values
echo -n '<value>' | base64

---

### Kubernetes

minikube start

###to view k8s dashboard on web
minikube dashboard

kubectl apply -f mysql-stateful-set.yml
kubectl apply -f employee-deployment.yml

kubectl apply -f k8s

kubectl delete -f k8s

###To get pods
kubectl get pod

### To expose IP outside Kubernetes cluster

kubectl expose pod employee-service-54d55c6bc7-zjckc --type=NodePort --name=employee-service --port=8080 --target-port=8080

kubectl get svc employee-service

minikube service employee-lb

kubectl logs -f <pod-name>
