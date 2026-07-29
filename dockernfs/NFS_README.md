### Create an Ubuntu VM in Virtual box. Keep network interface as "Bridged" for easy access of VM IP from host machine.

### Install NFS server on the VM using following command:

```bash

sudo apt update
sudo apt install nfs-kernel-server
sudo mkdir -p /mnt/nfs_share
sudo chmod 777 /mnt/nfs_share/
sudo vim /etc/exports
```

Enter following line in the `/etc/exports` file

```
/mnt/nfs_share *(rw,sync,no_subtree_check)
```
`*` here means, its open for all client in network

Export the NFS Share Directory

```bash
sudo exportfs -a
sudo systemctl restart nfs-kernel-server
```

### Mount NFS server dir in local machine

Example NFS Server IP: 192.168.1.8

```bash
# Check the mount details in server
showmount -e 192.168.1.8

# Create local dir
sudo mkdir -p /private/nfs

# Mount the NFS volume to local path
sudo mount -t nfs -o nolocks,resvport,locallocks 192.168.1.8:/mnt/nfs_share /private/nfs

# Testing..
cd /private/nfs
touch test.txt

# Now go to NFS server
ls /mnt/nfs_share

# File should have synced here 
```
