package com.aluracursos.screenmatch26.principal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String>listaDenombres = Arrays.asList("Bredra","Luis","Maria Fernanda","Eric","Genesys");

        listaDenombres.stream()
                        .sorted()
                        .limit(4)
                        .filter(n->n.startsWith("L"))
                        .map(String::toUpperCase)
                        .forEach(System.out::println);

    }
}
