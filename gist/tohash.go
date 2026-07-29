package main

import (
	"crypto/sha1"
	"encoding/hex"
	"fmt"
)

func calculateHash(input string) string {
	hasher := sha1.New()
	hasher.Write([]byte(input))
	hash := hasher.Sum(nil)
	return hex.EncodeToString(hash)
}

func main() {
	bigString := "YourBigStringHere"
	hash := calculateHash(bigString)
	fmt.Println("Hash:", hash)
}
