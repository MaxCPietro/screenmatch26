package com.aluracursos.screenmatch26;


import com.aluracursos.screenmatch26.principal.Inicio2;
import com.aluracursos.screenmatch26.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Screenmatch26ApplicationConsola implements CommandLineRunner {

	@Autowired
    private SerieRepository repository;
    public static void main(String[] args) {
		SpringApplication.run(Screenmatch26ApplicationConsola.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Inicio2 inicio = new Inicio2(repository);
        inicio.muestraElMenu2();

        }
}
