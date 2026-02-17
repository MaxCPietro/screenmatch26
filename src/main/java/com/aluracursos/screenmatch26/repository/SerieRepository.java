package com.aluracursos.screenmatch26.repository;

import com.aluracursos.screenmatch26.dto.EpisodioDTO;
import com.aluracursos.screenmatch26.model.Categoria;
import com.aluracursos.screenmatch26.model.Episodio;
import com.aluracursos.screenmatch26.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {

    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    @Query("SELECT s FROM Serie s WHERE s.totalDeTemporadas >= :totalTemporadas AND s.evaluacion >= :evaluacion")
    List<Serie>seriePorTemporadaYEvaluacion(int totalTemporadas, Double evaluacion);

    @Query("""
           SELECT e
           FROM Serie s
           JOIN s.episodios e
            WHERE LOWER(e.titulo) LIKE LOWER(CONCAT('%', :nombreEpisodio, '%'))
           """)
    List<Episodio>episodiosPorNombre(String nombreEpisodio);

    @Query("""
           SELECT e
           FROM Serie s
           JOIN s.episodios e
           WHERE s = :serie
           ORDER BY e.evaluacion DESC
           LIMIT 5
           """)
    List<Episodio>top5Episdios(Serie serie);

    //Series nuevas con JPQL
    @Query("""
              SELECT s
              FROM Serie s JOIN s.episodios e
              GROUP BY s ORDER BY MAX(e.fechaDeLanzamiento)
              DESC LIMIT 5
          """)
    List<Serie> lanzamientosMasRecientes ();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporada = :numeroTemporada")
    List<Episodio> obtenerTemporadasPorNumero(Long id, Long numeroTemporada);

}
