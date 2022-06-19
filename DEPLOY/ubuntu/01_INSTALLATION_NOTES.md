# Setup 

`IMPORTANT`: The executions will be in two environments. Some on Ubuntu 22 (server) and others in your local machine  (client).  

- The client is your own machine (Windows, Linux, Mac), the commands will be executed with the terminal of your local machine

- the server is the 'remote' environment.  You will execute the commands , typing directly on the virtual machine or connecting with ssh to the server

---

## Initial Configuration

### 1. Update the system (server)
```bash
sudo apt-get -y update && sudo apt-get -y upgrade
```

### 2. Install ssh cliente and server (server)
```bash
sudo apt-get -y install openssh-client openssh-server
```

### 3. Install basic tools (server)
```bash
 sudo apt install -y vim git curl jq gcc tmux wget
```

### 4. Instal net tools (server)
```bash
sudo apt install -y net-tools vsftpd
```

### 5. Check the status of the firewall. the first time will send the message inactive  (server)
```bash
sudo ufw status
```

### 5.1 If the status is inactive , execute the following command 
```bash
sudo ufw enable
```

### 5.2 Check the status of the firewall. the second time will send the message active  (server)
```bash
sudo ufw status
```

### 5.3 Allow the trafic for the port 22
```bash
sudo ufw allow ssh
```

### 5.4 Check the status of the firewall. the 3th time will send the message active and show the open ports  (server)
```bash
sudo ufw status
```
---

## Connecting with SSH

### 1. Get your ip address (server)
```bash
ip addr show
```

### 2.  Try to connect to the server with ssh in your own machine with the ip (client)
```bash
ssh <YOUR_USER>@<IP_OF_VIRTUAL_MACHINE>
```

### 3. For windows, copy the ip addres to hosts files inside C:\Windows\System32\drivers\etc .  For this sample the alias will be ubuntu22.server.cloud (client)
```bash
<VIRTUAL_MACHINE_IP_ADDRES> ubuntu22.server.cloud  
```

### 4  Try to connect to the server with ssh in your own machine with the an alias define in the hosts file (client)
```bash
ssh <YOUR_USER>@<ALIAS_DEFINE_IN_HOSTS_FILE>
```

### 5. For this sample the alias is ubuntu22.cloud.server, this name will be define in hosts file (client)
```bash
<IP_OF_YOUR_VIRUTAL_MACHINE> ubuntu22.cloud.server
```

---

## Open ports


### 1.  Backup the original file  (server)
```bash
sudo cp /etc/vsftpd.conf  /etc/vsftpd.conf.<CURRENT_DATE>.bak

sudo cp /etc/vsftpd.conf  /etc/vsftpd.conf.20220601.bak
```

### 2. Edith the ftp configuration file (server)
```bash
sudo vim /etc/vsftpd.conf 
```


### 3. Settings in configuration file (server)
```conf
anonymous_enable=NO

write_enable=YES

anon_upload_enable=YES

chroot_local_user=YES
chroot_list_enable=YES
chroot_list_file=/etc/vsftpd.chroot_list
```

### 4. Create the user list and add your user (server)
```bash
sudo vim /etc/vsftpd.chroot_list
```

### 4.1 Type the name of the allowed users
```bash
<THE_ALLOW_USER_NAME>
```


### 5. restart teh service (server)
```bash
sudo systemctl restart vsftpd.service
```

sudo ufw status

sudo ufw allow ftp

sudo ufw status


---

## FTP from Local Host (Windows, Linux, Mac)

### 1. Go to target folder in the Spring Project and upload the jar file (client)
```bash
> cd target
> ftp ubuntu22.cloud.server
ftp> ls
ftp> cd Downloads
ftp> put springboot-todo-h2-api.jar
ftp> bye
```

---

## Setup Java Environment


### 1. Install Java (server)
```bash
sudo apt install -y openjdk-17-jdk-headless openjdk-17-jre-headless
```

```bash
vim .bashrc
```

### 2. Setup JAVA_HOME in the .bashrc file (server)
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

export PATH=$PATH:$JAVA_HOME/bin
```

```bash
source ~/.bashrc
```

```bash
echo $JAVA_HOME
```

### 3. Create a  Deployments folder (server)

```bash
mkdir ~/Deployments
cd ~/Deployments
```

### 4. Move the jar into Deployments (server)
```bash
mv ~/Downloads/springboot-todo-h2-api.jar ~/Deployments/
```


### 5. Run spring application (server)
```bash
java -jar springboot-todo-h2-api.jar.jar &
```

### 5.1 Send a curl, the response will be not found 404, because nothing is define to be out in this path 
```bash
curl localhost:8080 
```

### 5.2 Send a curl to get the definition of swagger, you must see a json response
```bash
curl http://localhost:8080/api/todoapp/v3/api-docs
```

### 5.3 Send a curl to get a json response, then pass it to jq command
```bash
http://localhost:8080/api/todoapp/v3/api-docs | jq
```

### 5.4  Set the curl response into a variable call response
```bash
response=$(http://localhost:8080/api/todoapp/v3/api-docs)
```

### 5.5  see the response  using echo and sending the output to jq command
```bash
echo $response | jq
```

### 5.6 see the status code of the response
```bash
curl --write-out "%{http_code}\n" --output /dev/null --silent   http://localhost:8080/api/todoapp/v3/api-docs
```

### Copy the status code and save it into a variable called http_code
```bash
http_code=$(curl --write-out "%{http_code}\n" --output /dev/null --silent   http://localhost:8080/api/todoapp/v3/api-docs) && echo $http_code
```

### echo the http_code
```bash
echo $http_code
```


### 6. Allow trafic on port 8080 (server)
```bash
sudo ufw  allow 8080
```

### 7. Test on your own machine, the response will be not found (client)
```bash
curl http://ubuntu22.cloud.server:8080
```

### 7.1 Test on your own machine, pointing to  api-docs, you must see a json response (client)
```bash
curl http://ubuntu22.cloud.server:8080/api/todoapp/v3/api-docs
```

### 7.2 Test on your own machine, pointing to api-docs and send the response to the jq command (client)
```bash
curl http://ubuntu22.cloud.server:8080/api/todoapp/v3/api-docs | jq
```

---

## Open port 80 and 443


### 1. Allow trafic on port 80, 443 (server)
```bash
sudo ufw  status
sudo ufw  allow 80
sudo ufw  allow 443

sudo ufw status
```

### 2. Redirect trafic to port 80 (server)
```bash
sudo iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080
```

### 3. Test on you local machine (client)
```bash
curl http://ubuntu22.cloud.server/api/todoapp/v3/api-docs
```


---

## Stop Java Application


### Get all processes
```bash
top
```

### Get the processes of some user
```bash
top -u <USERNAME_WHO_START_JAVA_APPLICATION>
```

### See the open ports
```bash
 sudo lsof -i -P -n | grep LISTEN
```

### Kill, process id
```bash
kill <PROCESS_ID_OF_JAVA_APPLICATION>
```