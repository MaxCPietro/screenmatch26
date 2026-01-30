package com.aluracursos.screenmatch26.principal;

import com.aluracursos.screenmatch26.model.DatosSerie;
import com.aluracursos.screenmatch26.model.DatosTemporada;
import com.aluracursos.screenmatch26.service.ConsumoAPI;
import com.aluracursos.screenmatch26.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//mostrar un menu para que usuario ingrese una serie
public class Inicio {

    //Atributos
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL_BASE ="https://www.omdbapi.com/?t=";
    private final String API_KEY="&apikey=93082e3f";
    private ConvierteDatos conversor = new ConvierteDatos();

    //metodo
    public void muestraElMenu(){
        System.out.println("Por favor escribe el nombre de la serie que deseas buscar: ");
        String nombreSerie = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE+nombreSerie.replace(" ", "+")+API_KEY);
        System.out.println("******DATOS API******");
        System.out.println("datos Json:" + json);

        var datos = conversor.obtenerDatos(json, DatosSerie.class);

        //Datos de Temporadas
        //cambio la url a url de episodio
        // ✅ CORRECTO
        List<DatosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= datos.TotalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(
                    URL_BASE+nombreSerie.replace(" ", "+")
                    +"&Season="+i +API_KEY);
            DatosTemporada temporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(temporada);
            //System.out.println("******DATOS SERIALIZADOS TEMPORADA******");
            System.out.println("datos TEMPORADA: " + temporada);
        }



    }
}
