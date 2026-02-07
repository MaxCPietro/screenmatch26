package com.aluracursos.screenmatch26;


import com.aluracursos.screenmatch26.principal.Inicio2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Screenmatch26Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Screenmatch26Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Inicio2 inicio = new Inicio2();
        inicio.muestraElMenu2();

        }
}
