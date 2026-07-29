
### Tag management service

This is a demo project to showcase the event mq management using NATs. Here on adding or deleting a tag in post svc send message to tag svc to update in its db.

#### Create local nats server

```bash
docker run -p 4222:4222 -p 8222:8222 -p 6222:6222 --name nats-server -ti nats:latest
```

#### Create virtual env

```bash
# Stay in root folder
python3 -m venv venv

#activate venv
source ./venv/bin/activate

# To confirm Venv
pip -V

# Install all dependency
cd tag_svc
pip install -r requirements.txt
```


#### Run tag ms (Terminal tab 1)

```bash
source ./venv/bin/activate

# make sure you are in venv
cd tag_svc

python3 tag_api.py
# Now use .http file in respective folder to call APIs
```

#### Run post ms (Terminal tab 2)

```bash
source ./venv/bin/activate
# make sure you are in venv
cd post_svc

python3 post_api.py
# Now use .http file in respective folder to call APIs
```
