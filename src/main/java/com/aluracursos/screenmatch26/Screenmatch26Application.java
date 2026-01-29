package com.aluracursos.screenmatch26;

import com.aluracursos.screenmatch26.model.DatosSerie;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.ConsumoAPI;
import service.ConvierteDatos;

@SpringBootApplication
public class Screenmatch26Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Screenmatch26Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
        ConsumoAPI consumoAPI = new ConsumoAPI();
        String json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=friends&apikey=93082e3f");
        System.out.println("******DATOS API******");
        System.out.println("datos Json:" + json);

        //Deserializer los dato del json atributos de clase
        ConvierteDatos conversor = new ConvierteDatos();
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println("******DATOS SERIALIZADOS******");
        System.out.println("datos SERIE:" + datos);

    }
}
