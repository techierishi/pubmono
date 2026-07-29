### Make docker image
`docker build -t pyapptest .`

### Instantiate above image to a container
`docker run --name pyapptestcn -dp 5001:5001 -v "/private/nfs:/data/" pyapptest`

Now hit `http://localhost:5001` from host to run the webapp inside the container

### To go inside the docker container

```bash
docker exec -it pyapptestcn /bin/sh
```
### Stop and delete container
```
docker stop pyapptestcn
docker rm pyapptestcn
```

### Delete Image
```
docker rmi pyapptest
```
