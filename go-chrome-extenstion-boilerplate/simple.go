package main

import "fmt"

func main() {
	var a int = 100
	var b int = 200
	var ret int
	ret = sum(a, b)
	fmt.Printf("Sum is : %d\n", ret)
}

func sum(num1, num2 int) int {
	return num1 + num2
}
