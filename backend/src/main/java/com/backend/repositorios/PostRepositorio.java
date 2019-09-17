package com.backend.repositorios;

import com.backend.entidades.Artista;
import com.backend.entidades.Biografia;
import com.backend.entidades.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepositorio extends JpaRepository<Post, Long> {

    Optional<ArrayList<Post>> findAllByBiografia(Biografia biografia);
    Optional<Post> findById (Long id);
    List<Post> findTop100ByOrderByIdDesc();




}
