package com.vdr.reservaciones.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.reservaciones.entities.Reservacion;
import com.vdr.reservaciones.enums.EstadoReserva;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
	List<Reservacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Reservacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoResgistro);
	
}
