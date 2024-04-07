package repository

import (
	"log"

	"github.com/FineiasAntonio/Toggers-API/config"
	"github.com/FineiasAntonio/Toggers-API/models"
	"github.com/google/uuid"
)

var DB = &config.DB

func FindAll() ([]models.User, error) {

	var user = models.User{
		ID:       uuid.Nil,
		Username: "",
		Email:    "",
	}

	var users []models.User = []models.User{}

	query := "SELECT * FROM users;"
	var Users []models.User

	rows, err := DB.Query(query)
	if err != nil {
		return nil, err
	}
	defer rows.Close()

	for rows.Next() {

		var ID uuid.UUID
		var username string
		var email string
		var password string

		if err := rows.Scan(&ID, &username, &email, &password); err != nil {
			return nil, err
		}

		user.ID = ID
		user.Username = username
		user.Email = email
		user.Password = password

		Users = append(users, user)
	}

	return Users, nil
}

func Create(user *models.User) (models.User, error) {
	query := "INSERT INTO users (ID, username, email, password) VALUES (?,?,?,?);"
	tx, err := DB.Begin()

	defer func() {
		if err != nil {
			tx.Rollback()
			return
		}
	}()

	_, err = tx.Exec(query, user.ID, user.Username, user.Email, user.Password)

	if err != nil {
		return models.User{}, err
	}

	log.Println("Registered")
	tx.Commit()
	return *user, nil
}
