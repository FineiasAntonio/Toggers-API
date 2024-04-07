package main

import (
	"log"
	"net/http"

	"github.com/FineiasAntonio/Toggers-API/handlers"
	"github.com/gorilla/mux"
)

const PORT = ":8080"

func main() {

	r := mux.NewRouter()

	r.HandleFunc("/", handlers.GetAllUsers).Methods("GET")
	r.HandleFunc("/", handlers.RegisterNewUser).Methods("POST")

	http.Handle("/", r)

	if err := http.ListenAndServe(PORT, nil); err != nil {
		log.Fatalln(err.Error())
	}

}
