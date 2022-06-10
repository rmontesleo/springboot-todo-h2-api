# Setup 

`IMPORTANT`: The executions will be in two environments. Some on Ubuntu 22 (server) and others in your local machine  (client).  

- The client is your own machine (Windows, Linux, Mac), the commands will be executed with the terminal of your local machine

- the server is the 'remote' environment.  You will execute the commands , typing directly on the virtual machine or connecting with ssh to the server



## Initial Configuration

### 1. Get your ip address (server)
```bash
ip addr show
```

### 2. Check the status of the firewall the first time, to see open ports (server)
```bash
sudo ufw status
```

### 3. Update the system (server)
```bash
sudo apt-get -y update && sudo apt-get -y upgrade
```

### 4. Install ssh cliente and server (server)
```bash
sudo apt-get -y install openssh-client openssh-server
```


### 5. Install basic tools (server)
```bash
 sudo apt install -y vim git curl jq gcc tmux wget
```


### 6. Instal net tools (server)
```bash
sudo apt install -y net-tools vsftpd
```

### 7. check the current status of the firewall  (server)
```bash
sudo ufw status
```

### 7.1 If the status is inactive , execute the following command 
```bash
sudo ufw enable
```

### 7.2 Allow the trafic for the port 22
```bash
sudo ufw allow ssh
```

### 7.3  Try to connect to the server with ssh in your own machine with the ip (client)
```bash
ssh <YOUR_USER>@<IP_OF_VIRTUAL_MACHINE>
```

### 7.4  Try to connect to the server with ssh in your own machine with the an alias define in the hosts file (client)
```bash
ssh <YOUR_USER>@<ALIAS_DEFINE_IN_HOSTS_FILE>
```

### 7.5 . For this sample the alias is ubuntu22.cloud.server, this name will be define in hosts file (client)
```bash
<IP_OF_YOUR_VIRUTAL_MACHINE>ubuntu22.cloud.server
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

### 4. Edith the user list and add your user (server)
```bash
sudo vim /etc/vsftpd.chroot_list
```

### 5. restart teh service (server)
```bash
sudo systemctl restart vsftpd.service
```

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

### 2. Setup JAVA_HOME in the .bashrc file (server)
```bash
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64

export PATH=$PATH:$JAVA_HOME/bin
```


### 3. Create a  Deployments folder (server)

```bash
mkdir ~/Deployments
cd ~/Deployments
```

### 4. Move the jar into Deployments (server)
```bash
mv ~/Downloads/springboot-todo-h2-api.jar ~/Deployments/springboot-todo-h2-api.jar
```


### 5. Run spring application (server)
```bash
java -jar springboot-todo-h2-api.jar.jar
```

### 6. Allow trafic on port 8080 (server)
```bash
sudo ufw  allow 8080
```

### 7. Test on your own machine (client)
```bash
curl http://ubuntu22.cloud.server:8080
```

---

## Open port 80 and 443


### 1. Allow trafic on port 80, 443 (server)
```bash
sudo ufw  allow 80
sudo ufw  allow 443
```


### 2. Redirect trafic to port 80 (server)
```bash
sudo iptables -t nat -I PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080
```

### 3. Test on you local machine (client)
```bash
curl http://ubuntu22.cloud.server:80
```
