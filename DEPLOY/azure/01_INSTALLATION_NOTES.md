# Installation notes on Ubuntu 22.04 LTS Virtual Machine


## Upload the jar file to azure vm 

### Copy the jar file from the client host to Virtual Machine on cloud using scp (client)
```bash
scp springboot-todo-h2-api.jar <LINUX_USERNAME>@<VIRTUAL_MACHINE_IP>:<FULL_PATH>

scp springboot-todo-h2-api.jar <LINUX_USERNAME>@<VIRTUAL_MACHINE_IP>:/home/<LINUX_USERNAME>
```

### Connect to vm (client)
```bash
ssh  <LINUX_USERNAME>@<VIRTUAL_MACHINE_IP>
```

---

## Setup Virtual machine

### Update system (server)
```bash
sudo apt-get -y update && sudo apt-get -y upgrade
```

### Crear Deployments folder (server)
```bash
mkdir ~/Deployments
```

### Move the jar file from home to Deployment folder (server)
```bash
mv  springboot-todo-h2-api.jar  ~/Deployments
```

### Install Java 17 on virtual machine (server)
```bash
sudo apt install -y openjdk-17-jdk-headless openjdk-17-jre-headless
```

### edit .bashrc file (server)
```bash
vim ~/.bashrc
```

### Set JAVA_HOME variable (server)
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

export PATH=$PATH:$JAVA_HOME/bin
```

### update the bashrc file (server)
```bash
source .bashrc
```

### Go to Deployment Folder (server)
```bash
cd ~/Deployments
```

### execute java program (server)
```bash
java -jar springboot-todo-h2-api.jar
```

--- 

## Test Java application

### curl the api (client)
```bash
curl http://<VIRTUAL_MACHINE_IP>:8080
```

### Go to Azure Portal and open port 8080 (client)

### Go to Azure Portal and open port 80 (client)


### test againt the api (client)
```bash
curl http://<VIRTUAL_MACHINE_IP>:8080
```

---

## Redirect and test on port 80

### curl the api (client)
```bash
curl http://<VIRTUAL_MACHINE_IP>:80
```

### On vm redirect from 80 to 8080 port (server)
```bash
sudo iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080
```


### curl the api again (client)
```bash
curl http://<VIRTUAL_MACHINE_IP>:80
```

### Test on Port 8080
```bash
curl http://<VIRTUAL_MACHINE_IP>:8080/api/todoapp/swagger-ui/index.html
```

### Test on Port 80
```bash
curl http://<VIRTUAL_MACHINE_IP>/api/todoapp/swagger-ui/index.html
```
