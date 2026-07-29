
### Internal
```
ls - The most frequently used command in Linux to list directories
pwd - Print working directory command in Linux
cd - Linux command to navigate through directories
mkdir - Command used to create directories in Linux
mv - Move or rename files in Linux
cp - Similar usage as mv but for copying files in Linux
rm - Delete files or directories
touch - Create blank/empty files or update datetime of the file
ln - Create symbolic links (shortcuts) to other files
cat - Display file contents on the terminal
clear - Clear the terminal display
echo - Print any text that follows the command
less - Linux command to display paged outputs in the terminal
man - Access manual pages for all Linux commands
uname - Linux command to get basic information about the OS
whoami - Get the active username
tar - Command to extract and compress files in Linux
grep - Search for a string within an output
head - Return the specified number of lines from the top
tail - Return the specified number of lines from the bottom
diff - Find the difference between two files
cmp - Allows you to check if two files are identical
comm - Combines the functionality of diff and cmp
sort - Linux command to sort the content of a file while outputting
export - Export environment variables in Linux
zip - Zip files in Linux
unzip - Unzip files in Linux
service - Linux command to start and stop services
ps - Display active processes
kill and killall - Kill active processes by process ID or name
df - Display disk filesystem information
mount - Mount file systems in Linux
chmod - Command to change file permissions
chown - Command for granting ownership of files or folders
ifconfig - Display network interfaces and IP addresses
traceroute - Trace all the network hops to reach the destination
wget - Direct download files from the internet
ufw - Firewall command
iptables - Base firewall for all other firewall utilities to interface with
apt, pacman, yum, rpm - Package managers depending on the distro
sudo - Command to escalate privileges in Linux
cal - View a command-line calendar
alias - Create custom shortcuts for your regularly used commands
dd - Majorly used for creating bootable USB sticks
whereis - Locate the binary, source, and manual pages for a command
whatis - Find what a command is used for
top - View active processes live with their system usage
useradd and usermod - Add new user or change existing users data
passwd - Create or update passwords for existing users
cat - This command can be used to get the contents of the file as an output in the Terminal window. You just need to write the cat command as shown sample screenshot and execute it.
aptitude - aptitude is highly powerful interface for Linux package management system.
cal - You can use cal command in Terminal window to see the calendar, as you can see in the following screenshot I have executed command to view the calendar of the current month and you can notice it highlighted the date as well.
bc - bc is another cool and useful command for Linux users as it allows you to enable command line calculator in Linux Terminal when you execude following command.
chage - The Linux command chage is an acronym for change age and it can be used to change the expiry information of the user’s password.
df - You can get all the information of your file system just by executing df command in the Terminal window.
du - Utility that allows users to analyze and report on disk usage within directories and files.
help - When you execute this help command in Terminal window, it will list all built-in commands you can use in shell.
pwd (Print Work Directory) - As the name Print Work Directory suggests, this command the path of the directory currently you’re working in. This command is very useful for all the Linux noobs and those who are new to Linux Terminal.
ls - I think I don’t need to introduce to this command as this is one of the commonly used commands in Terminal by Linux users.
factor - factor is a mathematical command  for Linux terminal which will give you all the possible factors of the decimal number you enter in the shell.
uname - uname is another useful Linux command to have as it displays Linux system information when executed in Terminal shell.
mkdir - mkdir <file name> command can be used to create a new folder in any directory using Linux Terminal. You can see in the following screenshot that I have created VGPM folder using mkdir command in Terminal shell.
gzip - You can compress any file from Terminal window using gzip <file name> command but it will remove original file from the directory. If you wish to keep the original file then use gzip -k <file name> instead as it will keep both original as well as new compressed file in the directory.
whatis - If you wish to know for what the particular Linux command can be used for then just execute the command whatis <command name> in Terminal shell and it will show you short one line description of that particular Linux command.
who - This one is for system administrators who handle and manage various users on Linux system. who command when executed in Terminal show the complete list of those users who are currently logged into Linux system.
free - free command can be used to check exactly what amount of storage is free and used in physical as well as swap memory in the system.
top - top is simple but useful command to monitor all the ongoing processes on the Linux system with the user name, priority level, unique process id and shared memory by each task.
sl - This one is just for bit fun during the work and not a useful command. When executed a steam engine passes through Terminal window. You can try it for fun!
banner - banner is another fun command for Linux Terminal when executed with banner <your text> will display whatever text you type will be displayed in big banner format as you can see in following screenshot.
aafire - How about putting the Terminal window on fire? Just fire the command aafire in the Terminal window and see the magic.
echo - echo command can be used to print any text you through with the command as you can see in the below screenshot.
finger - finger <user name> will display all the information about any user on the system such as last login of the user, home directory of the user and full name of the user account.
groups - If you want to know which groups the particular user is member of then execute groups <user name> command in Terminal window. It will show entire list of the groups a user is member of.
head - This command will list the first 10 lines of the file you through with head command in the Terminal window. If you want to see particular number of lines then use -n (number) option like head -n (any number) <file name> in Terminal shell just like I did in following case.
man - Here man stands for user manual and as the name suggests man <command name> will display the user manual for the particular command. It will display name of the command, ways in which command can be used and description of the command.
passwd - You can use passwd command to change the password for self or any user, just through the command passwd if you want to change password for youself and passwd <user name> if you want to change password for particular user.
w - w is the short and simple command which will help you view the list of currently logged in users.
whoami - This command will help you to find out which user is logged into system or who you are logged in as.
history - When fired into Terminal shell, history command will list all the commands used by you in serial numbered form. Using exclamation mark ! and serial number of the command will help you execute that particular command without need to writing whole command in the terminal.
login - If you want to switch user or want to create new session then fire this command in Terminal window and provide the details like login id and password as shown in screenshot below.
lscpu - This command will display all the CPU architecture information such as threads, sockets, cores and CPU count.
mv - mv (move) command can be used to move one file or directory to another file or directory. It is very useful command especially when you’re working on system administration.
ps - If you want to see the list of processes that are currently running for your session or for other users on the system then ps command is for you as it shows processes with their process identification numbers and in detail as well when you use ps -u command.
kill - You can use this command to kill the currently ongoing processes manually form the Terminal shell itself. You need unique PID i.e. process identification number to kill the process.
tail - tail <file name> command will display last 10 lines of the file in the Terminal window as an output. There is an option to last specific number of lines as you want with the command tail -n <number of lines> <file name> as shown in screenshot below.
cksum - cksum is a command to generate the checksum value for the file or stream of data thrown with command in Linux Terminal. You can also whether the download is corrupted or not if you are facing problem in running it.
cmp - If you ever need to do byte-by-byte comparison of the two files then cmp <file name 1> <file name 2> is the best Linux command for you.
env - env is a very useful shell command which can be used to display all the environment variable in the Linux Terminal window or running another task or program in custom environment without need to make any modifications in current session.
hostname - hostname command can be used to view the current host name and hostname <new name> can be used to change the current host name to new one.
hwclock - You can use hwclock or hwclock –set –date <DD/MM/YYYY> command to view hardware clock or set it to new date.
lshw - sudo lshw command can be used to invoke detailed hardware information of the system on which Linux is running. It gives you every small detail about hardware, just try it.
nano - nano is Linux command-line text editor just similar to Pico editor which many of you might have used for programming and other purposes. It is quite useful text editor with lots of features.
rm - rm <file name> command can be used to remove any file from the working directory. For better convenience you can use rm -i <file name> command as it will first ask for your confirmation before removing the file.
ifconfig - ifconfig is another useful Linux command which can be used to configure network interface on the system.
clear - clear is simple command for Linux Terminal shell, when executed it will clear the Terminal window for fresh start.
su - su <username> command can be used to switch to another account right from the Linux Terminal window.
wget - wget <file path> is very useful command to download any file from the internet and best part is download works in background so that you can continue working on your task.
yes - yes “your text” command is used to display a text message entered with yes command repeatedly on Terminal window until you stop it using CTRL + c keyboard shortcut.
last - When executed last command will display the list of last logged in users into the system as an output in Linux Terminal.
locate - locate command is an reliable and arguably better alternative to find command to locate any file on the system.
iostat - If you ever need to monitor system input/output devices then iostat command can be very useful for you as it displays all the stats of the CPU as well as I/O devices in Terminal window itself.
kmod - You can use kmod list command to manage all the Linux Kernel modules as this command will display all the currently loaded modules on the system.
lsusb - lsusb command will show information about all the USB buses connected to the hardware and external USB devices connected to them as you can see in screenshot below.
pstree - pstree command displays all the currently running processes in the tree format on Linux Terminal window.
sudo - If you need to run any command as a root user or root permissions then just add sudo at the start of any command.
apt - apt (Advanced Package Tool) is Linux command which helps user to interact with the packaging system as you can see in following screenshot.
zip - You can use zip command to compress one or more files as you can see in the screenshot below. It is simple but useful command to compress any number of files in a go.
unzip - To extract files from compressed zip file use unzip <file name> command in Terminal shell. You can also use this command to extract files from multiple compressed files from the particular directory.
shutdown - You can use shutdown command to turn of the system directly from Terminal shell. This command will shutdown the system exactly one minute after being executed. You can use shutdown -c command to cancel shutdown.
dir - dir (directory) command can be used to view the list of all directories and folders present in current working directory.
cd - cd command helps you to access particular directory or folder from the file system. You can also use cd .. command to go back to root.
reboot - As the name suggests you can use reboot command to restart or shutdown the system from the Terminal window. There are several options available with this command as you can see in following screenshot.
sort - sort <file name> command will help you sort file or arrange any record in particular order generally according to their ASCII values.
undefined
tac - tac <file name> command will display the contents of the file in reverse orders as you can see in below screenshot.
exit - exit command can be used to close the Terminal shell window directly from the command-line.
ionice - ionice command will help you get or set I/O scheduling class and priority for the particular process.
diff - diff <file name1> <file name2> command will compare the two directories and will display difference between them as shown in following screenshot.
dmidecode - There are many commands available for Linux to retrieve hardware information but if you want information of a particular hardware component then dmidecode is the command for you. It offers various options and you can view them using dmidecode –help.
expr - If you want to perform quick calculations during your work then expr is really useful command for you. You can do calculations as shown in below screenshots with more options.
gunzip - gunzip <filename> command can be used to extract or restore files compressed with gzip command.
hostnamectl - hostnamectl command can be used to access system information, change the system hostname and other related settings.
iptable - iptables is a simple Linux Terminal based firewall tool which helps manage both incoming and outgoing traffic using tables.
killall - killall <process name> command will kill all the programs matching the processes name thrown with the killall command.
netstat - This command is for those who need to monitor incoming and outgoing network connections continuously. netstat command displays the network status, routing tables and interface statistics.
lsof - lsof command will help you view all the open file related to your application in the Linux Terminal window itself. There are several options to customize the output and you can see the whole list in the below screenshot.
bzip2 - You can use bzip2 <file name> command in the Terminal window to compress any file to .bz2 file and use bzip2 -d <compressed file name> command to extract the files from compressed file.
service - service command will display results of System V init scripts in the Terminal window. You can view the status of particular service or all the services as shown in below screenshot.
vmstat - vmstat command will display systems virtual memory usage on Terminal window.
mpstat - When executed mpstat command will display all the information about CPU utilization and performance stats on Linux Terminal window.
usermod - If you want edit or modify attributes of already created user account then usermod <options> login is the best command for you.
touch - Using touch command in Terminal window you can create empty files in file system and you can also change time and date i.e. is timestamp of recently accessed files as well as directories.
uniq - uniq is a standard Linux Terminal command when thrown with file, filters the repeated lines in the file.
wc - wc command reads the file thrown with the command and displays word and line count of the file.
pmap - pmap <pid> command display the memory map of the pid you provide. You can also view memory map for multiple processes..
rpm - rpm -i <package name>.rpm command can be used to install rpm based packages on Linux. To remove rpm package use rpm -e <package name> command in Terminal shell.
nproc - nproc [option] command will display the number of processing units allotted to the currently running process.
scp - scp acronym for Secure Copy is the Linux command which can be used to copy files and directories between hosts on the network.
sleep - sleep <Number> command will delay or pause the execution of command for particular amount of time i.e. specified with sleep command.
split - If you need to breakdown large file into small file then use split [option].. [file [prefix]] command in the Linux Terminal.
stat - You can view the status of a file or an entire file system using stat <file or file system name> command in Linux Terminal. You can also use other options as listed in the screenshot.
lsblk - lsblk command reads the sysfs filesystem and displays the block device information on the Terminal window.
hdparm - Using hdparm command you can handle hard disk and other disk devices in the Linux using Terminal shell.
chrt - chrt [option] priority [argument..] command is used for manipulating the real-time attributes of the process.
useradd - useradd [optaons] login command will help you add user account into your system
userdel - userdel [option] login command will let you delete any user account from the system.
usermod - Using usermod [options] login command you can modify any user account present on the system.
```

### External
```
wget - Wget
curl - Curl
tmux - Tmux
kind - Kind
minikube - Minikube
kubectl - Kubectl
docker - Docker
docker-compose - Docker compose
fzf - File search
ripgrep - File content search
pet - (github.com/knqyf263/pet) Snippet manager
resh - (github.com/curusarn/resh) Bash history search
lnav - TUI log viewer
lazygit - Git TUI
lazydocker - Docker TUI
k9s - K8s Tui
htop - Better top
limactl - Create VMs in Mac
colima - Docker VM manager
qemu - Create VMs
terraform - Terraform
ansible - Ansible
vault - Password manager
nvim - NeoVim
git - Git
bat - Better cat
tree - File tree
alpaca - (github.com/samuong/alpaca) Local proxy server 
tldr - (github.com/isacikgoz/tldr)
jq - (github.com/jqlang/jq) JSON viewer
```

### Programming 
```
go
node/bin/deno
python
lua
gcc/zig
java
```

### Networking

```
ip -	Manipulating routing to assigning and configuring network parameters
traceroute	- Identify the route taken by packets to reach the host
tracepath -	Gets maximum transmission unit while tracing the path to the network host
ping - Often used to check the connectivity between the host and the server
ss - Gets details about network sockets
dig	- Gives all the necessary information about the DNS name server
host - Prints IP address of a specific domain and viscera
hostname - Mostly used to print and change the hostname
curl -	Transfers data over the network by supporting various protocols
mtr -	A combination of ping and traceroute is used to diagnose the network
whois -	Gets info about registered domains, IP addresses, name servers, and more
ifplugstatus -	Detects the link status of a local Ethernet device
iftop -	Monitors stats related to bandwidth
tcpdump -	Packet sniffing and analyzing utility used to capture, analyze and filter network traffic
ethtool -	Allows users to configure Ethernet devices
nmcli -	Troubleshooting utility for network connections
nmap -	Primarily used to audit network security
bmon -	An open-source utility to monitor real-time bandwidth
firewalld	- CLI tool to configure rules of Firewall
iperf -	Utility to measure network performance and tuning
speedtest-cli -	CLI utility of speedtest.net to check internet speeds
vnstat -	Mostly used to monitor network traffic and bandwidth consumption
ssh - Secure Shell command in Linux
telnet - Check open port in remote server
```




