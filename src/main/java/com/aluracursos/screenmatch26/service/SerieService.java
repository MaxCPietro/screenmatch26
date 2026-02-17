package com.aluracursos.screenmatch26.service;

import com.aluracursos.screenmatch26.dto.SerieDTO;
import com.aluracursos.screenmatch26.model.Serie;
import com.aluracursos.screenmatch26.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SerieService {
    //Contenerá todas las reglas de negocio

    //inyección de dependencias
    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> obtenerSerie() {
        return convierteDatos(serieRepository.findAll());}


    public List<SerieDTO> obtenerTop5() {
        return convierteDatos(serieRepository.findTop5ByOrderByEvaluacionDesc());
    }

    public List <SerieDTO> obtenerLanzamientosMasRecientes(){
        return convierteDatos(serieRepository.lanzamientosMasRecientes());
    }
    //conversor de datos
    public List<SerieDTO> convierteDatos(List<Serie> serie) {
        return serie.stream()
                .map(s -> new SerieDTO(
                        s.getTitulo(),
                        s.getTotalDeTemporadas(),
                        s.getEvaluacion(),
                        s.getPoster(),
                        s.getGenero(),
                        s.getActores(),
                        s.getSinopsis()
                ))
                .collect(Collectors.toList());
    }
}
