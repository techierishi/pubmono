# GoEncDec
Golang encrypt decrypt a file using cli

## Usage

### Flags:
```-e``` For encryption
```-d``` For decryption
```-file``` File path


### Encryption
```bash
./goencdec -e -file=/Users/Desktop/test.txt
```
Now enter the key to encrypt the file with key


### Decryption
```bash
./goencdec -d -file=/Users/Desktop/test.txt.enc
```
Now enter the key to decrypt the file with key (same key used for encryption)
