package com.aluracursos.screenmatch26.principal;

import com.aluracursos.screenmatch26.model.DatosSerie;
import com.aluracursos.screenmatch26.model.DatosTemporada;
import com.aluracursos.screenmatch26.model.Serie;
import com.aluracursos.screenmatch26.service.ConsumoAPI;
import com.aluracursos.screenmatch26.service.ConvierteDatos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class Inicio2 {
        private Scanner teclado = new Scanner(System.in);
        private ConsumoAPI consumoApi = new ConsumoAPI();
        private final String URL_BASE = "https://www.omdbapi.com/?t=";
        private final String API_KEY = "&apikey=93082e3f";
        private ConvierteDatos conversor = new ConvierteDatos();
        private List <DatosSerie> listaDatosSeries = new ArrayList<>();

        public void muestraElMenu2() {
            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                                  
                    0 - Salir
                    """;
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1:
                        buscarSerieWeb();
                        break;
                    case 2:
                        buscarEpisodioPorSerie();
                        break;
                    case 3:
                        mostrarSeriesBuscadas();
                        break;

                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            }

        }
    private DatosSerie getDatosSerie() {
            System.out.println("Escribe el nombre de la serie que deseas buscar");
            var nombreSerie = teclado.nextLine();
            var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
            System.out.println(json);
            DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
            return datos;
        }
        private void buscarEpisodioPorSerie() {
            DatosSerie datosSerie = getDatosSerie();
            List<DatosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= datosSerie.totalDeTemporadas(); i++) {
                var json = consumoApi.obtenerDatos(URL_BASE + datosSerie.titulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
        }
        private void buscarSerieWeb() {
            DatosSerie datos  = getDatosSerie();
            listaDatosSeries.add(datos);
            System.out.println(datos);
        }

        private void mostrarSeriesBuscadas() {
            List <Serie> listaseries = new ArrayList<>();
            listaseries = listaDatosSeries.stream()
                .map(s -> new Serie(s))
                    .collect(Collectors.toList());

            listaseries.stream()
                    .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
        }








}
