package com.aluracursos.screenmatch26.dto;

import com.aluracursos.screenmatch26.model.Categoria;
import com.fasterxml.jackson.annotation.JsonAlias;

public record SerieDTO (
        String titulo,
        Integer totalDeTemporadas,
        Double evaluacion,
        String poster,
        Categoria genero,
        String actores,
        String sinopsis
){
}
