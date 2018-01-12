# JupyterHub on Ubuntu

This guide explains how to install JupyterHub on Ubuntu with Google authenticator and Docker spawner

## Install Anaconda

Download Anaconda package for Python 3.6 version from [https://repo.continuum.io/archive/Anaconda3-5.0.1-Linux-x86_64.sh](https://repo.continuum.io/archive/Anaconda3-5.0.1-Linux-x86_64.sh)

```
bash /path/to/Anaconda3-5.0.1-Linux-x86_64.sh
``` 

You could choose where Anaconda is installed. 

You could run ```source ~/.bashrc``` after Anaconda is installed to make all of the Anaconda executables available to the shell

## Install JupyterHub

```
sudo apt-get install npm nodejs-legacy
conda install -c conda-forge jupyterhub  # installs jupyterhub and proxy
conda install notebook  # needed if running the notebook servers locally
```

## Install SudoSpawner

Since JupyterHub needs to spawn processes as other users, the simplest way is to run it as root, spawning user servers with [setuid](http://linux.die.net/man/2/setuid). But this isn't especially safe, because you have a process running on the public web as root.

A more prudent way to run the server while preserving functionality is to create a dedicated user with sudo access restricted to launching and monitoring single-user servers.

Download Sudospawner through ```https://github.com/jupyterhub/sudospawner.git```, and install it with ```pip install -e . ```.

Create user that will be used to run the process, instead of root. ```sudo useradd -m grant```

Configure sudoers to have the grants to run the process. For this guide, all users in ```jupyter``` group will be able to run single jupyter servers. To create a new group, run ```sudo groupadd jupyter```

Edit ```/etc/sudoers``` by ```sudo visudo ```, and add at the bottom
```
Cmnd_Alias JUPYTER_CMD = /path/to/sudospawner

grant ALL=(%jupyterhub) NOPASSWD:JUPYTER_CMD
```
Add Anaconda bin directory to ```secure_path```

```Defaults secure_path = /sbin:/bin:/usr/sbin:/usr/bin:/path/to/anaconda3/bin```

Add all of the users you want to be able to launch a jupyter notebook to the group ```jupyterhub```
```sudo usermod -a -G jupyterhub grant```

By default, Ubuntu creates ```shadow``` group, to use PAM authenticator, add the user to ```shadow``` group
```sudo usermod -a -G shadow grant```

## Configure JupyterHub

Create a place for JupyterHub to store all its things ...
```
sudo mkdir ~/jupyterhub
sudo chown grant ~/jupyterhub

sudo -u grant jupyterhub --generate-config

c.JupyterHub.spawner_class = 'sudospawner.SudoSpawner'

```

## Install Docker

Run the following commands to install Docker
```
sudo apt-get remove docker docker-engine docker.io

sudo apt-get update
sudo apt-get install apt-transport-https ca-certificates curl software-properties-common

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
   $(lsb_release -cs) \
   stable"
   
sudo apt-get update
sudo apt-get install docker-ce

```

## Install DockerSpawner

To install DockerSpawner, run the command ```pip install dockerspawner```

Edit ```jupyterhub_config.py```, add the following configurations 
```
c.JupyterHub.spawner_class = 'dockerspawner.DockerSpawner
c.DockerSpawner.image = 'jupyter/all-spark-notebook'
c.DockerSpawner.host_ip = '0.0.0.0'

```

Download the docker image
```sudo docker pull jupyter/all-spark-notebook```

Add the user to ```docker``` group to make the user call the docker commands
```sudo usermod -a -G docker grant```

## Install Google authenticator

Install jupyterhub/oauthenticator ```pip3 install oauthenticator```

Create "OAuth Client ID" at Google developer console [https://console.cloud.google.com/apis/credentials](https://console.cloud.google.com/apis/credentials)

In Credentials/Restrictions, add ```http://${hostname}:8000``` to "Authorized JavaScript origins", add ```http://${hostname}:8000/hub/oauth_callback``` to "Authorized redirect URIs"

Note: Google won't allow IP address appear in redirect URIs, so you could use a DNS service to associate hostname with a public IP address

If successfully, you get "Client ID" and "Client secret"

Edit ```jupyterhub_config.py```, add the following configurations
```
from oauthenticator.google import GoogleOAuthenticator
c.JupyterHub.authenticator_class = GoogleOAuthenticator
c.GoogleOAuthenticator.oauth_callback_url = 'http://${hostname}:8000/hub/oauth_callback'
c.GoogleOAuthenticator.client_id = '${google.credentials.client.id}'
c.GoogleOAuthenticator.client_secret = '${google.credentials.client.secret}'
```

## Other configurations

```
c.JupyterHub.hub_ip = '10.10.0.32'
c.JupyterHub.ip = '10.10.0.32'
c.JupyterHub.admin_access = True
c.Authenticator.admin_users = {'grant'}
```