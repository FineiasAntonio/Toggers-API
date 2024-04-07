package config

import (
	"database/sql"
	"log"

	_ "github.com/go-sql-driver/mysql"
)

var DB sql.DB = *Conn()

func Conn() (db *sql.DB) {

	connStr := "root:root@tcp(localhost:3306)/toggers-db"

	db, err := sql.Open("mysql", connStr)

	err = db.Ping()

	if err != nil {
		log.Println(err.Error())
	}

	if err != nil {
		log.Println("Database connection error", err.Error())
	}

	log.Println("sucessful database connection")
	return

}
