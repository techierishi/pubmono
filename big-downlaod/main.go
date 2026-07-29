package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"math/rand"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
	"github.com/google/uuid"
	"github.com/tidwall/buntdb"
)

type DownloadFile struct {
	FileName    string `json:"fileName"`
	IsProcessed bool   `json:"isProcessed"`
}

var db *buntdb.DB = initDB()

func checkDownload(c *gin.Context) {
	db.Update(func(tx *buntdb.Tx) error {
		fileId := c.Param("fileId")
		fileDetail, err := tx.Get(fmt.Sprintf("%s%s", "files-", fileId))
		if err != nil {
			return err
		}
		var downloadFile DownloadFile
		json.Unmarshal([]byte(fileDetail), &downloadFile)

		fmt.Printf("fileDetail %s\n", fileDetail)
		c.JSON(http.StatusOK, gin.H{
			"data": fileDetail,
		})
		return err
	})
}
func startDownload(c *gin.Context) {
	id := uuid.New()
	fmt.Println(id.String())

	err := db.Update(func(tx *buntdb.Tx) error {
		fileName := fmt.Sprintf("%s%s", id.String(), ".txt")
		downloadFile := DownloadFile{
			FileName:    fileName,
			IsProcessed: false,
		}
		data, _ := json.Marshal(downloadFile)
		key := fmt.Sprintf("%s%s", "files-", id.String())
		_, _, err := tx.Set(key, string(data), nil)
		return err
	})
	if err != nil {
		log.Fatal(err)
	}

	go prepareFile(id.String())
	c.JSON(http.StatusOK, gin.H{
		"id": id.String(),
	})
}

func prepareFile(id string) {

	rand.Seed(time.Now().UnixNano())
	min := 20
	max := 60
	r := rand.Intn(max-min+1) + min
	time.Sleep(time.Duration(r) * time.Second)
	fileName := fmt.Sprintf("%s%s%s", "downloads/", id, ".txt")
	fileContent := fmt.Sprintf("%s%s%s", "File content for ", fileName, ".txt")
	err := ioutil.WriteFile(fileName, []byte(fileContent), 0644)
	if err != nil {
		panic(err)
	}

	db.Update(func(tx *buntdb.Tx) error {
		fileDetail, err := tx.Get(fmt.Sprintf("%s%s", "files-", id))
		if err != nil {
			return err
		}
		var downloadFile DownloadFile
		json.Unmarshal([]byte(fileDetail), &downloadFile)

		downloadFile.IsProcessed = true
		data, _ := json.Marshal(downloadFile)
		key := fmt.Sprintf("%s%s", "files-", id)
		_, _, err = tx.Set(key, string(data), nil)
		fmt.Printf("fileDetail %s\n", fileDetail)

		return err
	})
}

func checkFile() {
	id := uuid.New()
	fmt.Println(id.String())
}

func initDB() *buntdb.DB {
	db, err := buntdb.Open("data.db")
	if err != nil {
		log.Fatal(err)
	}
	// defer db.Close()

	return db
}
func main() {
	r := gin.Default()
	r.Static("/ui", "./ui")
	r.StaticFS("/downloads", http.Dir("downloads"))

	r.GET("/ping", func(c *gin.Context) {
		c.JSON(http.StatusOK, gin.H{
			"message": "pong",
		})
	})

	r.GET("/startDownload", startDownload)
	r.GET("/checkDownload/:fileId", checkDownload)

	r.Run()
}
