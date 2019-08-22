package com.backend.repositorios;

import com.backend.entidades.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepositorio extends JpaRepository<Notificacion, Long> {

}
