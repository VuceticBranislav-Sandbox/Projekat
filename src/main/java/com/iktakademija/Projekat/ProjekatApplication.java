package com.iktakademija.Projekat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjekatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjekatApplication.class, args);
	}
	
	// TODO check that all adder funcions chenc valid atribut flags.eg. notnull
	// TODO dodati sto vise qeuery pretraga mesto funkcija
	// TODO check that all adders add all atributes.
	// TODO cekirati da li je validan sav kod. Bughunt
	// TODO dodati postma za sve
	// TODO dodati atribute anotacija svuda
	// TODO Dodati case insensitiv putanje za end pointe
	// TODO proveriti da li su svi atributu inicializirani u konstruktoru
	// TODO proveriti warninge na kreiranju tabela. wtf?
	// TODO Dodati validaciju svih mailova na  @ . simbole
	// TODO za sve promene podataka treba proslediti pasword za potvrdu ?
	// TODO kod PUT i POST nije vodjeno racuna o nulabilnim vrednostima. Poprviti to	
	// TODO Dodati logovoe
	//	@JsonIgnore
	//	private final Logger logger = LoggerFactory.getLogger(UploadController.class); // Uppload.Controller.class === this.getClass()	
	//	logger.debug("This is a debug message");
	//	logger.info("This is an info message");
	//	logger.warn("This is a warn message");
	//	logger.error("This is an error message");
}
