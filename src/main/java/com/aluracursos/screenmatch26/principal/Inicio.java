package com.aluracursos.screenmatch26.principal;

import com.aluracursos.screenmatch26.model.DatosEpisodio;
import com.aluracursos.screenmatch26.model.DatosSerie;
import com.aluracursos.screenmatch26.model.DatosTemporada;
import com.aluracursos.screenmatch26.model.Episodio;
import com.aluracursos.screenmatch26.service.ConsumoAPI;
import com.aluracursos.screenmatch26.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println("datos Json:" + json+"\n");

        var datos = conversor.obtenerDatos(json, DatosSerie.class);

        //Datos de Temporadas
        //cambio la url a url de episodio
        // ✅ CORRECTO
        List<DatosTemporada> temporadas = new ArrayList<>();

        for (int i = 1; i <= datos.totalDeTemporadas(); i++) {
            json = consumoAPI.obtenerDatos(
                    URL_BASE + nombreSerie.replace(" ", "+")
                            + "&Season=" + i + API_KEY);
            DatosTemporada temporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(temporada);
            //System.out.println("******DATOS SERIALIZADOS TEMPORADA******");
            //System.out.println("datos TEMPORADA: " + temporada);
        }
        //Mostrar solo el título de los episodios para las temporadas
        /*for (int j = 1; j< datos.TotalDeTemporadas(); j++) {
            List<DatosEpisodio> episodiosTemporadas = temporadas.get(j).episodio();
            for (int k = 1; k < episodiosTemporadas.size(); k++) {
                System.out.println("Titulo del episodio:"+  k + " "+ episodiosTemporadas.get(k).titulo());
            }
        }*/

        //Muestra datos de solo el titulo de los episodios de las temporadas usando expresiones lambdas
        /*temporadas.forEach(t ->t.episodio().forEach(e-> System.out.println(
                    "episodio: "+ e.numeroEpisodio() + " " + e.titulo() )));*/

        //Convertir todas las informaciones a una unica lista del tipo datos episodio usando expresiones lambdas
        List<DatosEpisodio> datosEpisodios = temporadas.stream()
                                                       .flatMap(lu -> lu.episodio().stream())
                                                       .collect(Collectors.toList());
        //datosEpisodios.forEach(System.out::println);

        //Top 5 Episodios
        /*System.out.println("top 5 Episodios de la serie: " + nombreSerie);
        datosEpisodios.stream()
                      .filter(e->!e.evaluacion().equalsIgnoreCase("N/A"))
                      .sorted(Comparator.comparing(DatosEpisodio::evaluacion).reversed())
                      .limit(5)
                      .forEach(System.out::println);*/


        //Conviertiendo los datos a lista de tipo episodio.
        List<Episodio> episodio = temporadas.stream()
                                    .flatMap(t->t.episodio().stream()
                                    .map(d -> new Episodio(t.numero(),d)))
                                    .collect(Collectors.toList());

        //episodio.forEach((System.out::println));

        //Busqueda de Episodios a partir de x año
        /*System.out.println("Indica el año a partir del año a partir del cual quieres ver los episodios: ");
        var fecha = teclado.nextInt();
        teclado.nextLine();

        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);

        //Formateo la fecha a tiempo local
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy");

        episodio.stream()
                .filter(e->(e.getFechaDeLanzamiento()!= null && e.getFechaDeLanzamiento().isAfter(fechaBusqueda)))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getTemporada() + " " +
                        "Episodio: "  + e.getTitulo() + " " +
                        "Fecha de Lanzamiento: " + e.getFechaDeLanzamiento().format(formatter)

                ));*/

        //Busca episodio por pedazo de titulo
        /*System.out.println("Escriba el 'pedazo' de titulo del episodio que desea ver");
        var pedazoTitulo = teclado.nextLine().toLowerCase().trim();
        Optional<Episodio> episodioBuscado= episodio.stream()
                                                .filter(e->e.getTitulo() != null && !e.getTitulo().isEmpty())
                                                .filter(e ->e.getTitulo().toLowerCase().contains(pedazoTitulo))
                                                .findFirst();
        if (episodioBuscado.isPresent()) {
            System.out.println("Episodio Encontrado");
            System.out.println("Los datos son: "+ episodioBuscado.get());
        } else  {
            System.out.println("Episodio no encontrado");
        }*/

        //Creando estadísticas para cada temporada
        /*Map<Integer, Double> evaluacionesPorTemporada = episodio.stream()
                                                                .filter(e->e.getEvaluacion()>0.0)
                                                                .collect(Collectors.groupingBy(Episodio::getTemporada
                                                                        , Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println("Evaluaciones por Temporada: "+ evaluacionesPorTemporada);*/

        //10 Creando estadística básicas
        DoubleSummaryStatistics est =  episodio.stream()
                                               .filter(e->e.getEvaluacion()>0.0)
                                               .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media de las Evaluaciones: "+ est.getAverage());
        System.out.println("Episodio mejor Evaluado: "+ est.getMax());
        System.out.println("Episodio peor evaluado: "+ est.getMin());

    }
}
