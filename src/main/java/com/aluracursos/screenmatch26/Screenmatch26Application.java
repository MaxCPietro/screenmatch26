package com.aluracursos.screenmatch26;

import com.aluracursos.screenmatch26.model.DatosEpisodio;
import com.aluracursos.screenmatch26.model.DatosSerie;
import com.aluracursos.screenmatch26.model.DatosTemporada;
import com.aluracursos.screenmatch26.principal.Inicio;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.aluracursos.screenmatch26.service.ConsumoAPI;
import com.aluracursos.screenmatch26.service.ConvierteDatos;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Screenmatch26Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Screenmatch26Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        Inicio inicio = new Inicio();
        inicio.muestraElMenu();
        }
}
