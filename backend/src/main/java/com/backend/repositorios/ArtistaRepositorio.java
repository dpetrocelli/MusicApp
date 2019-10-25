package com.backend.repositorios;

import com.backend.entidades.Artista;
import com.backend.entidades.Biografia;
import com.backend.entidades.Usuario;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface ArtistaRepositorio extends JpaRepository<Artista, Long> , JpaSpecificationExecutor<Artista> {
    boolean existsByUsuario(Usuario usuario);
    Optional<Artista> findByUsuario(Usuario usuario);

    Artista findByNombre(String nombre);


    @Query("SELECT a FROM Artista a WHERE a.nombre like %:status% and a.generoMusical like %:genero%")
    List<Artista> FindAllLikeBusquedaGenero( @Param("status") String status, @Param("genero") String genero);

    @Query("SELECT a FROM Artista a WHERE a.nombre like %:busqueda%")
    List<Artista> porBusqueda( @Param("busqueda") String busqueda);



}
