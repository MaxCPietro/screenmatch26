package com.aluracursos.screenmatch26;

import com.aluracursos.screenmatch26.model.DatosEpisodio;
import com.aluracursos.screenmatch26.model.DatosSerie;
import com.aluracursos.screenmatch26.model.DatosTemporada;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.ConsumoAPI;
import service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;

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
        System.out.println("datos SERIE: " + datos);

        //Datos Episodio
        //cambio la url a url de episodio
        json = consumoAPI.obtenerDatos("https://www.omdbapi.com/?t=friends&season=1&episode=1&apikey=93082e3f");
        DatosEpisodio episodios =  conversor.obtenerDatos(json, DatosEpisodio.class);
        System.out.println("******DATOS SERIALIZADOS EPISODIO******");
        System.out.println("datos EPISODIO: " + episodios);

        //Datos de Temporadas
        //cambio la url a url de episodio
        // ✅ CORRECTO
        List<DatosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= datos.TotalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(
                    "https://www.omdbapi.com/?t=friends&season=" + i + "&apikey=93082e3f"
            );

            DatosTemporada temporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(temporada);
        }

        System.out.println("******DATOS SERIALIZADOS TEMPORADA******");
        System.out.println("datos TEMPORADA: " + temporadas);


    }
}
