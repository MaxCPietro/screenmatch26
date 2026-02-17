package com.aluracursos.screenmatch26.controller;

import com.aluracursos.screenmatch26.dto.EpisodioDTO;
import com.aluracursos.screenmatch26.dto.SerieDTO;
import com.aluracursos.screenmatch26.repository.SerieRepository;
import com.aluracursos.screenmatch26.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series") //indentifca que es la URL BASE
public class SerieController {

    @Autowired
    private SerieService serie;

    @GetMapping()
    public List<SerieDTO> obtenerSerie() {
        return serie.obtenerSerie();
    }

    @GetMapping("/top5")
    public List<SerieDTO> ObtenerTop5() {
        return serie.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> ObtenerLanzamientosMasRecientes(){
        return serie.obtenerLanzamientosMasRecientes();
    }

    @GetMapping("/{id}")
    public SerieDTO obtenerPorID(@PathVariable Long id){
        return serie.obtenerPorID(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obtenerTodasLasTemporadas(@PathVariable Long id){
        return serie.obtenerTodasLasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDTO> obtenerTemporadasPorNumero(@PathVariable Long id, @PathVariable Long numeroTemporada){
        return serie.obtenerTemporadasPorNumero(id,numeroTemporada);
    }

    @GetMapping("/categoria/{nombreGenero}")
    public List<SerieDTO> obtenerSeriesPorCategoria(@PathVariable String nombreGenero){
        return serie.obtenerSeriesPorCategoria(nombreGenero);
    }
}
