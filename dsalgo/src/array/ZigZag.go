package main

import "fmt"

func swap(arr []int, i,j int){
    temp := arr[i]
    arr[i] = arr[j]
    arr[j] = temp
}
func main() {
    slic := []int{4, 3, 7, 8, 6, 2, 1}
    for i:=0; i<len(slic)-1; i++ {
        j := i+1
        if(i+2)%2 == 0 {
            if slic[i] > slic[j]{
               swap(slic, i, j)
            }
        }else{
            if slic[i] < slic[j]{
                swap(slic, i, j)
            }
        }
    }
    
    fmt.Println(slic)
}
