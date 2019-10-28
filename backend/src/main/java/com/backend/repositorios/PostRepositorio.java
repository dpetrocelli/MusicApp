package com.backend.repositorios;

import com.backend.entidades.Artista;
import com.backend.entidades.Biografia;
import com.backend.entidades.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepositorio extends JpaRepository<Post, Long> {

    Optional<ArrayList<Post>> findAllByBiografia(Biografia biografia);
    Optional<Post> findById (Long id);
    List<Post> findTop10ByOrderByFechaCreacionDesc();

    @Query("SELECT p FROM Post p WHERE p.informacion like %:informacion% or p.username like %:informacion%")
    List<Post> FindAllLike(@Param("informacion") String informacion);

    @Query("SELECT p FROM Post p WHERE p.informacion like %:informacion% or p.username like %:username%")
    List<Post> FindAllLike2(@Param("informacion") String informacion, @Param("username") String username);




}
