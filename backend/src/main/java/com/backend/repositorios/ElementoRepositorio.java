package com.backend.repositorios;

import com.backend.entidades.Elemento;
import com.backend.entidades.Instrumento;
import com.backend.entidades.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ElementoRepositorio extends JpaRepository<Elemento, Long> {
    //
    List<Elemento> findAllByPost(Post post);


}
