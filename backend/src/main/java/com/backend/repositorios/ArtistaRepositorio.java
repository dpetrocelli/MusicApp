package com.backend.repositorios;

import com.backend.entidades.Artista;
import com.backend.entidades.Biografia;
import com.backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ArtistaRepositorio extends JpaRepository<Artista, Long> {
    boolean existsByUsuario(Usuario usuario);
    Optional<Artista> findByUsuario(Usuario usuario);

    Artista findByNombre(String nombre);

    @Query("SELECT a FROM Artista a WHERE a.nombre like %:status% and a.generoMusical like %:genero%")
    List<Artista> FindAllLike( @Param("status") String status, @Param("genero") String genero);


}
