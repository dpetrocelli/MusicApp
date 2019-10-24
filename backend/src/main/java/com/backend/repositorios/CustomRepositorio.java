package com.backend.repositorios;

import com.backend.entidades.Artista;

import java.util.List;

public interface CustomRepositorio {
    List<Artista> findUserByEmails(String busqueda, String zona, String instrumento, String genero);
}
