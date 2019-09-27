package com.backend.repositorios;



import com.backend.entidades.NotificacionBandaUsuario;
import com.backend.entidades.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface NotificacionBandaUsuarioRepositorio extends JpaRepository<NotificacionBandaUsuario, Long> {
    /*Optional<ArrayList<Promocion>> findAllByComercio(Long id);

    Boolean existsByComercio(Long comercio);
*/
    Optional<ArrayList<NotificacionBandaUsuario>> findAllByNombreDestinoAndTipoDestino(@Param("nombreDestino") String idDestino, @Param("tipoDestino") int tipoDestino);


}
