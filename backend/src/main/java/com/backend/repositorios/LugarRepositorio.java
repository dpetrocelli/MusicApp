package com.backend.repositorios;

import com.backend.entidades.Comercio;
import com.backend.entidades.Lugar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface LugarRepositorio extends JpaRepository<Lugar, Long>, JpaSpecificationExecutor<Lugar> {
    Optional<Lugar> findByNombre(String ni);
    List<Lugar> findAllByComercio(Comercio id);
    boolean existsByNombre(String ni);
}
