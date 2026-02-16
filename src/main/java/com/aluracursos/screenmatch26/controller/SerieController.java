package com.aluracursos.screenmatch26.controller;

import com.aluracursos.screenmatch26.dto.SerieDTO;
import com.aluracursos.screenmatch26.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SerieController {
    //inyección de dependencias
    @Autowired
    private SerieRepository serieRepository;

    @GetMapping("/series")
    public List<SerieDTO> obtenerSerie() {
        return serieRepository.findAll()
                .stream()
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

    @GetMapping("/inicio")
    public String muestraMensaje() {
        return "Serie inicio de temporada";
    }

}
