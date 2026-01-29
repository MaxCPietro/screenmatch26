package com.aluracursos.screenmatch26.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosSerie(
        @JsonAlias("Title") String Titulo,
        @JsonAlias("totalSeasons") Integer TotalDeTemporadas,
        @JsonAlias("imdbRating") String evaluacion
) {
}
