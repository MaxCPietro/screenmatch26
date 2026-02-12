package com.aluracursos.screenmatch26.principal;

import com.aluracursos.screenmatch26.model.DatosSerie;
import com.aluracursos.screenmatch26.model.DatosTemporada;
import com.aluracursos.screenmatch26.model.Episodio;
import com.aluracursos.screenmatch26.model.Serie;
import com.aluracursos.screenmatch26.repository.SerieRepository;
import com.aluracursos.screenmatch26.service.ConsumoAPI;
import com.aluracursos.screenmatch26.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

public class Inicio2 {
        private Scanner teclado = new Scanner(System.in);
        private ConsumoAPI consumoApi = new ConsumoAPI();
        private final String URL_BASE = "https://www.omdbapi.com/?t=";
        private final String API_KEY = "&apikey=93082e3f";
        private ConvierteDatos conversor = new ConvierteDatos();
        private List <DatosSerie> listaDatosSeries = new ArrayList<>();
        private SerieRepository repository;
        private List<Serie> series;

    public Inicio2(SerieRepository repository) {
        this.repository = repository;
    }

    public void muestraElMenu2() {
            var opcion = -1;
            while (opcion != 0) {
                var menu = """
                    1 - Buscar series 
                    2 - Buscar episodios
                    3 - Mostrar series buscadas
                    4 - Buscar Series por Titulo
                    5 - Top 5 Mejores Series
                                  
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
                    case 4:
                        buscarSeriesPorTitulo();
                        break;
                    case 5:
                        buscarTop5Series();
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
            mostrarSeriesBuscadas();
            System.out.println("Escribe el nombre de la serie que quieres guardar los ep en la BDD:");
            var nombreSerie = teclado.nextLine();

            Optional<Serie> serie = series.stream()
                    .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
                    .findFirst();
            if (serie.isPresent()) {
                var serieEncontrada = serie.get();
                List<DatosTemporada> temporadas = new ArrayList<>();

                for (int i = 1; i <= serieEncontrada.getTotalDeTemporadas(); i++) {
                    var json =
                            consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                    DatosTemporada datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                    temporadas.add(datosTemporada);
                }
                temporadas.forEach(System.out::println);
                List<Episodio> episodios = temporadas.stream()
                        .flatMap(d->d.episodio().stream()
                                .map(e->new Episodio(d.numero(),e)))
                        .collect(Collectors.toList());
                serieEncontrada.setEpisodios(episodios);
                repository.save(serieEncontrada);
            }






        }
        private void buscarSerieWeb() {
            DatosSerie datos  = getDatosSerie();
            Serie serie = new Serie(datos);
            repository.save(serie);
            //listaDatosSeries.add(datos);
            System.out.println(datos);
        }

        private void mostrarSeriesBuscadas() {
            series = repository.findAll();

            /*List <Serie> listaseries = new ArrayList<>();
            listaseries = listaDatosSeries.stream()
                .map(s -> new Serie(s))
                    .collect(Collectors.toList());*/

            series.stream()
                    .sorted(Comparator.comparing(Serie::getGenero))
                    .forEach(System.out::println);
        }
    //Opcion 4
    private void buscarSeriesPorTitulo() {
        System.out.println("Escribe el nombre de la serie que quieres buscar en la BDD:");
        var nombreSerie = teclado.nextLine();

        Optional<Serie> serieBuscada = repository.findByTituloContainsIgnoreCase(nombreSerie.toLowerCase());

        if (serieBuscada.isPresent()) {
            System.out.println("la serie buscada es: " + serieBuscada.get().getTitulo());
        } else {
            System.out.println("Serie no encontrada");
        }
    }

    //Opcion 5
    private void buscarTop5Series() {
        List <Serie> topSerie = repository.findTop5ByOrderByEvaluacionDesc();
        topSerie.forEach(s->
                System.out.println("Serie: " + s.getTitulo() + "Evaluacion: " + s.getEvaluacion()));
    }
}
