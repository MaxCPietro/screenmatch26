package com.aluracursos.screenmatch26.model;

public enum Categoria {
    ACCION("Action","Acción"),
    ROMANCE("Romance","Romance"),
    COMEDIA("Comedy","Comedia"),
    DRAMA("Drama","Drama"),
    CRiMEN("Crime","Crímen");


    private String categoriaOmdb;
    private String categoriaEspanol;

    Categoria(String categoriaOmdb,  String categoriaEspanol) {
        this.categoriaOmdb = categoriaOmdb;
        this.categoriaEspanol = categoriaEspanol;
    }

    //si la categoría coincide se hace la transformación al tipo string
    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna Categoria Encontrada: " + text);
    }

    //si la categoría coincide se hace la transformación al tipo string
    public static Categoria fromEspanol(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaEspanol.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Ninguna Categoria Encontrada: " + text);
    }

}
