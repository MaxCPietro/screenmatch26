package com.aluracursos.screenmatch26.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase);
}
