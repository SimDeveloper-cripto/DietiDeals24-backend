package com.exam.ingsw.dietideals24;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: IITEMREPOSITORY --> PER OGNI QUERY, L'ASTA DEVE AVERE "ACTIVE == TRUE", ALTRIMENTI NON HA SENSO
// TODO: IITEMREPOSITORY --> IMPLEMENTARE QUERY A RIGA 41 (TROVARE SOLO ITEMS PER CUI L'UTENTE HA FATTO RICHIESTA (NON DEVE AVERLI MESSI ALL'ASTA))

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}