package com.backend.repositorios;

import com.backend.entidades.Artista;
import com.backend.entidades.Banda;
import com.backend.entidades.Instrumento;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@Transactional
public interface BandaRepositorio extends JpaRepository<Banda, Long> , JpaSpecificationExecutor<Banda> {
    Optional<Banda> findByNombre(String ni);
    Optional<ArrayList<Banda>> findAllByartistaLider(Artista artista);
    boolean existsByNombre(String ni);
    boolean existsByArtistaLider (Artista artista);

    @Query("SELECT b FROM Banda b WHERE b.nombre like %:status%")
    List<Banda> FindAllLike( @Param("status") String status);

    @Query("SELECT a FROM Artista a WHERE a.banda=:banda")
    List<Artista> obtenerTodosArtistasDeBanda( @Param("banda") Set<Banda> banda);


}
