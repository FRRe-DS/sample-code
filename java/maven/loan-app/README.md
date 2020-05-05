Desarrollo de Aplicaciones Cliente-Servidor
==

Microservices
--------------------------

### Docker

Start building docker images for every services, simply run following command on root directory

```shell
mvn clean install
```
Launch services using `docker-compose`

```shell
docker-compose up --build -d
```

Explore services at:

  - Eureka Server: `http://localhost:8761`
  - Edge Server: `http://localhost:8765`
  - Service A: `http://localhost:8083/greeting-a`
  - Service B: `http://localhost:8084/greeting-b`
  - Greeting Service: `http://localhost:8085/greeting`
  - Greeting Service from Gateway: `http://localhost:8765/api/greeting/greeting`

<!-- Run on Kubernetes
---
  - Create Secrets
    - `create secret generic mysql01-pass --from-literal=password=ThePassword`
    - `create secret generic mysql02-pass --from-literal=password=ThePassword`
  - Create Deployment
    - `kubectl create -f kubernate-depoyment.yaml`
  - Clean up
    - `kubectl delete deployment,service,pvc -l app=loan-app` -->
