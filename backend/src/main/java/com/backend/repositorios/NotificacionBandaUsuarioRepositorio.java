package com.backend.repositorios;



import com.backend.entidades.NotificacionBandaUsuario;
import com.backend.entidades.Promocion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionBandaUsuarioRepositorio extends JpaRepository<NotificacionBandaUsuario, Long> {
    /*Optional<ArrayList<Promocion>> findAllByComercio(Long id);

    Boolean existsByComercio(Long comercio);
*/
    Optional<ArrayList<NotificacionBandaUsuario>> findAllByNombreDestinoAndTipoDestinoOrderByFechaNotificacionDesc(@Param("nombreDestino") String idDestino, @Param("tipoDestino") int tipoDestino);
    Optional<ArrayList<NotificacionBandaUsuario>> findAllByNombreDestinoOrderByFechaNotificacionDesc(String nombre);
    @Query("SELECT n FROM NotificacionBandaUsuario n WHERE n.nombreDestino = :status order by n.fechaNotificacion desc")
    ArrayList<NotificacionBandaUsuario> findAllByNombreDestino(
            @Param("status") String status);

    @Query("SELECT n FROM NotificacionBandaUsuario n WHERE n.nombreDestino = :nombreDestino order by n.fechaNotificacion desc")
    ArrayList<NotificacionBandaUsuario>  buscarNombreDestino (@Param("nombreDestino") String pepe);

    @Query("SELECT n FROM NotificacionBandaUsuario n WHERE n.nombreDestino = :nombreDestino and n.tipoDestino = :tipoDestino order by n.fechaNotificacion desc")
    ArrayList<NotificacionBandaUsuario>  buscarPorDuenioBanda (@Param("nombreDestino") String pepe,
                                                               @Param("tipoDestino") Integer tipo);

}
