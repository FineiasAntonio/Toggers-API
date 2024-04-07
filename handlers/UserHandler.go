package handlers

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"

	"github.com/FineiasAntonio/Toggers-API/models"
	"github.com/FineiasAntonio/Toggers-API/repository"
	"github.com/google/uuid"
)

func GetAllUsers(w http.ResponseWriter, r *http.Request) {

	users, err := repository.FindAll()

	if err != nil {
		log.Fatalln(err.Error())
	}

	json, err := json.Marshal(users)

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
	pessoa.ID = uuid.New()

	pessoa, err := repository.Create(&pessoa)

	if err != nil {
		log.Println(err.Error())
	}

	fmt.Println(pessoa)
	response, err := json.Marshal(pessoa)

	if err != nil {
		log.Println(err.Error())
	}

	w.WriteHeader(http.StatusCreated)
	w.Write(response)

}
