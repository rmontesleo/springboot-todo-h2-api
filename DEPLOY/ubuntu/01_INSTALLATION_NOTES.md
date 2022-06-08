# Setup 

## Initial Configuration

### 1. Get your ip address
```bash
ip addr show
```

### 2. Check the status of the firewall the first time, to see open ports
```bash
sudo ufw status
```

### 3. Update the system
```bash
sudo apt-get -y update && sudo apt-get -y upgrade
```

### 4. Install ssh cliente and server
```bash
sudo apt-get -y install openssh-client openssh-server
```


### 5. Switch to your local computer (Windows, Mac or Linux)  and login with ssh and continue the execution.


### 6. Install basic tools
```bash
 sudo apt install -y vim git curl jq gcc tmux wget
```


### 7. Instal net tools
```bash
sudo apt install -y net-tools vsftpd
```

### 8. check the current status of the firewall 
```bash
sudo ufw status
```

---

## Open ports


### 1.  Backup the original file
```bash
sudo cp /etc/vsftpd.conf  /etc/vsftpd.conf.<CURRENT_DATE>.bak

sudo cp /etc/vsftpd.conf  /etc/vsftpd.conf.20220601.bak
```

### 2. Edith the ftp configuration file
```bash
sudo vim /etc/vsftpd.conf 
```


### 3. Settings in configuration file
```conf
anonymous_enable=NO

write_enable=YES

anon_upload_enable=YES

chroot_local_user=YES
chroot_list_enable=YES
chroot_list_file=/etc/vsftpd.chroot_list
```

### 4. Edith the user list and add your user
```bash
sudo vim /etc/vsftpd.chroot_list
```

### 5. restart teh service
```bash
sudo systemctl restart vsftpd.service
```

---

## FTP from Local Host (Windows, Linux, Mac)

### 1. Go to target folder and upload the jar file
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


### 1. Install Java
```bash
sudo apt install -y openjdk-17-jdk-headless openjdk-17-jre-headless
```

### 2. Setup JAVA_HOME
```bash
#export JAVA_HOME=/usr/lib/jvm/msopenjdk-17-amd64

export PATH=$PATH:$JAVA_HOME/bin
```


### 3. Create a  Deployments folder

```bash
mkdir ~/Deployments
cd ~/Deployments
```

### 4. Move the jar into Deployments
```bash
mv ~/Downloads/springboot-todo-h2-api.jar ~/Deployments/springboot-todo-h2-api.jar
```


### 5. Run spring application
```bash
java -jar springboot-todo-h2-api.jar.jar
```

### 6. Allow trafic on port 8080
```bash
sudo ufw  allow 8080
```

### 7. Test on your localhost 
```bash

```


---

## Open port 80 and 443


### 1. Allow trafic on port 80, 443
```bash
sudo ufw  allow 80
sudo ufw  allow 443
```


### 2. Redirect trafic to port 80
```bash
sudo iptables -A PREROUTING -t nat -i eth0 -p tcp --dport 80 -j REDIRECT --to-port 8080
```

### 3. Test on your localhost 
```bash

```
