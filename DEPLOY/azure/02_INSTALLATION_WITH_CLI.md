# Deploy Spring Application on Azure Virtual Machine with the a Java environment 

The following instructions follow from a Linux, Mac or WSL environment

## Create you azure virtual machine

### 1. Create the resource Group
```bash
az group create --name springtodoapp-rg
```

### 2. See the available images related with java
```bash
az vm image list --all --offer Java --output table
```

### 3. See the sizes of the virtual machines
```bash
## see all sizes of the virtual machines
az vm list-sizes --location east-us2 --output table

## use the less command
az vm list-sizes --location east-us2 --output table | less
```

### 4. Create the virtual machine
```bash
az vm create \
--resource-group springtodoapp-rg \
--name vm-linux-springapp \
--image tidalmediainc:minecraft-java-ubuntu-20-minimal:minecraft-java-ubuntu-20-minimal:1.0.1 \
--size "Standard_B1ms"
--public-ip-sku Standard \
--admin-username vmadmin \
--generate-ssh-keys \
--ssh-key-values /home/<YOUR_USER_NAME>/.ssh/springapp \
--verbose
```

### 5. Upload jar file
```bash
scp -i  ~/.ssh/springapp.private  target/springboot-todo-h2-api.jar vmadmin@<VM_IP>:/home/vmadmin
```

### 6. Open port 80
```bash
az vm open-port --port 80  \
--resource-group springtodoapp-rg \
--name vm-linux-springapp \
--priority 100 \
--nsg-name webPort
```


### 7. Open port 8080
```bash
az vm open-port --port 8080  \
--resource-group springtodoapp-rg \
--name vm-linux-springapp \
--priority 200 \
--nsg-name apiPort
```

---

## Start the Java application

### 1 Login to the virtual machine
```bash
ssh -i  ~/.ssh/springapp.private vmadmin@<VM_IP>
```

### 2. Start java application (server)
```bash
java -jar springboot-todo-h2-api.jar &
```

### 3. Test the api (server)
```bash
curl localhost:8080/api/todoapp/v3/api-docs
```


### 4. Test the api on port 8080, Open from a web browser (client)
```bash
http://<VM_IP>:8080/api/todoapp/swagger-ui.html
```

---

## Testing from Port 80

### 1. Redirect from 80 to 8080 (server)
```bash
sudo iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080
```

### 2. Test the api on port 80,  (client)
```bash
curl http://<VM_IP>/api/todoapp/v3/api-docs
```

### 3. Test the api on port 80, open from a web browser (client)
```bash
curl http://<VM_IP>/api/todoapp/swagger-ui.html
```

---

## See some information about your VM

### 1. See the ip of your vm
```bash
az vm list-ip-addresses --resource-group springtodoapp-rg --name vm-linux-springapp --output table
```

### 2. get information of vm-linux-springapp in the resource group springtodoapp-rg
```bash
az vm show --resource-group springtodoapp-rg --name vm-linux-springapp
```

---

## Delete your resource group
```bash
az group delete --resource-group springtodoapp-rg
```
