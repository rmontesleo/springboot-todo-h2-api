# Installation notes on Ubuntu 22.04 LTS Virtual Machine


## Upload the jar file to azure vm 

### 1. Copy the jar file from the client host to Virtual Machine on cloud using scp (client)
```bash
scp springboot-todo-h2-api.jar <LINUX_USERNAME>@<VIRTUAL_MACHINE_IP>:<FULL_PATH>

scp springboot-todo-h2-api.jar <LINUX_USERNAME>@<VIRTUAL_MACHINE_IP>:/home/<LINUX_USERNAME>
```

### 2. Connect to vm (client)
```bash
ssh  <LINUX_USERNAME>@<VIRTUAL_MACHINE_IP>
```

---

## Setup Virtual machine

### 1. Update system (server)
```bash
sudo apt-get -y update && sudo apt-get -y upgrade
```

### 2. Crear Deployments folder (server)
```bash
mkdir ~/Deployments
```

### 3. Move the jar file from home to Deployment folder (server)
```bash
mv  springboot-todo-h2-api.jar  ~/Deployments
```

### 4. Install Java 17 on virtual machine (server)
```bash
sudo apt install -y openjdk-17-jdk-headless openjdk-17-jre-headless
```

### 5. Edit .bashrc file (server)
```bash
vim ~/.bashrc
```

### 6. Set JAVA_HOME variable (server)
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

export PATH=$PATH:$JAVA_HOME/bin
```

### 7. Update the bashrc file (server)
```bash
source .bashrc
```

### 8. Go to Deployment Folder (server)
```bash
cd ~/Deployments
```

### 9. execute java program (server)
```bash
java -jar springboot-todo-h2-api.jar
```

--- 

## Test Java application

### 1. curl the api (client)
```bash
curl http://<VIRTUAL_MACHINE_IP>:8080/api/todoapp/v3/api-docs
```

### 2. Go to Azure Portal and open port 8080 (client)

### 3. Go to Azure Portal and open port 80 (client)


### 4. test againt the api (client)
```bash
curl http://<VIRTUAL_MACHINE_IP>:8080/api/todoapp/v3/api-docs
```

---

## Redirect and test on port 80

### 1. curl the api (client)
```bash
curl http://<VIRTUAL_MACHINE_IP>/api/todoapp/v3/api-docs
```

### 2. On vm redirect from 80 to 8080 port (server)
```bash
sudo iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080
```

### 3. curl the api again (client)
```bash
curl http://<VIRTUAL_MACHINE_IP>/api/todoapp/v3/api-docs
```

### 4. Test on Port 8080. Open the following URL on a web Browser (Client)
```bash
http://<VIRTUAL_MACHINE_IP>:8080/api/todoapp/swagger-ui/index.html
```

### Test on Port 80. Open the following URL on a web Browser (Client)
```bash
http://<VIRTUAL_MACHINE_IP>/api/todoapp/swagger-ui/index.html
```
