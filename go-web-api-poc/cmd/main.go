// The only goal of the main to to be

package main

import (
	"fmt"
	"log"
	"net/http"
	"time"

	"github.com/techierishi/gowebapi/pkg/repository"
	"github.com/techierishi/gowebapi/pkg/routes"
)

func main() {

	// create Data Access Object
	dao := repository.NewDAO()

	// pass it into serverhandler this gives all handles access to the dao
	// This also intainsiates all the handlers to the routes
	srv := routes.NewServer(dao)

	server := &http.Server{

		Addr:    ":8383",
		Handler: srv,

		// Good practice: enforce timeouts for servers you create!
		WriteTimeout: 15 * time.Second,
		ReadTimeout:  15 * time.Second,
	}
	// listen and server at port :8080
	fmt.Println("starting server at port " + server.Addr)
	if err := server.ListenAndServe(); err != nil {
		log.Fatal(err)
	}

}
