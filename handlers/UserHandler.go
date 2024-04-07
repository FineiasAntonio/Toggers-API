package handlers

import (
	"encoding/json"
	"log"
	"net/http"

	"github.com/FineiasAntonio/Toggers-API/models"
)

var controle = 1

func GetAllUsers(w http.ResponseWriter, r *http.Request) {
	pessoa := models.User{
		ID:       "kasd-21ma",
		Username: "Teste",
		Email:    "fieias9219@gmail.com",
	}

	json, err := json.Marshal(pessoa)

	if err != nil {
		log.Fatalln("Error while marshaling json data")
	}

	w.Write(json)
}

func RegisterNewUser(w http.ResponseWriter, r *http.Request) {
	if err := r.ParseForm(); err != nil {
		http.Error(w, "Error while reading data", http.StatusInternalServerError)
		return
	}

	pessoa := models.User{}

	json.NewDecoder(r.Body).Decode(&pessoa)
	pessoa.ID = "uuid-temp"

	response, err := json.Marshal(pessoa)

	if err != nil {
		log.Println(err.Error())
	}

	w.WriteHeader(http.StatusCreated)
	w.Write(response)

}
