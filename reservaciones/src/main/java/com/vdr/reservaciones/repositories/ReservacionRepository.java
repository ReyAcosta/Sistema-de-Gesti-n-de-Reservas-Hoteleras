package com.vdr.reservaciones.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.reservaciones.entities.Reservacion;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
	List<Reservacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
}
