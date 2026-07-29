### Simple JS Todo App

![todo](todo.png)


#### PATH 1 (Run directly in local machine)
<hr>

```bash
npm i
node index.js
```

<hr>


#### PATH 2 (Run in local machine using Docker)

```bash

# To build and push the images [Skip if already done]
docker build --rm -t techierishi/jstodo:latest .
docker login -u techierishi
docker push techierishi/jstodo

# To see the images
docker images

# To run the container
docker run -d -p 3001:3000 --name jstodo-container techierishi/jstodo:latest

# To see docker ps and logs
docker ps -a
docker logs jstodo-container

# Open following URL in browser
http://127.0.0.1:3001/

# Clean up
docker rm $(docker stop jstodo-container) && docker rmi --force techierishi/jstodo:latest


```

<hr>

#### PATH 3 (Run in local k8s cluster using `kind`)

```bash

# Install kind for creating k8s cluster. Docker is prerequisite. [Skip if done]
brew install kind

# Create cluster
kind create cluster --name jstodo-cluster 

# Set context
kubectl config set-context jstodo-cluster  
kubectl cluster-info --context jstodo-cluster

# To get cluster nodes
kubectl get nodes

# Deploy yamls
kubectl apply -f cicd/yamls/config.yaml
kubectl apply -f cicd/yamls/webapp.yaml

# To get pods in cluster
kubectl get pods
kubectl describe pod <pod-name>
kubectl logs <pod-name> 

# Expose service to host machine
kubectl port-forward service/jstodo-service 3001:3000

# Open following URL in browser
http://127.0.0.1:3001/

# Delete cluster
kind delete cluster --name jstodo-cluster 
```
<hr>

#### PATH 4 (CI-CD using Jenkins)
Use `cidcd/Jenkinsfile` to create the pipeline and run it