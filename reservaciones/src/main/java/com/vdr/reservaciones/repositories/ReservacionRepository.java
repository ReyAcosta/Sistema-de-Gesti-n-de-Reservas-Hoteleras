package com.vdr.reservaciones.repositories;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.reservaciones.entities.Reservacion;
import com.vdr.reservaciones.enums.EstadoReserva;

import feign.Param;

@Repository
public interface ReservacionRepository extends JpaRepository<Reservacion, Long> {
	List<Reservacion> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Reservacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoResgistro);
	
	boolean existsByIdHuespedAndEstadoRegistroAndEstadoReserva(Long idHuesped, EstadoRegistro estadoRegistro, EstadoReserva estadoReserva);
	
	boolean existsByIdHabitacionAndEstadoRegistro(Long idHuesped, EstadoRegistro estadoRegistro);
	
	boolean existsByIdHuespedAndEstadoRegistro(Long idHuesped, EstadoRegistro estadoRegistro);
	@Query(value=""" 
			SELECT COUNT(*) 
			FROM RESERVACIONES R
			WHERE R.ID_HABITACION = :idHabitacion
			AND R.ESTADO_REGISTRO = :estadoRegistro
			AND R.ESTADO_RESERVA = :estadoReserva
			AND :fechaInicioNueva < R.FECHA_FIN
			AND :fechaFinNueva > R.FECHA_INICIO
			""",
			nativeQuery =true)
	Long chocaHorario(
			@Param("fechaInicioNueva")LocalDateTime fechaInicioNueva, 
			@Param("fechaFinNueva") LocalDateTime fechaFinNueva, 
			@Param("idHabitacion")Long idHabitacion,
			@Param("estadoRegistro")String estadoRegistro,
			@Param("estadoReserva")String estadoReserva);
	
	
	Optional<Reservacion> findByIdHuespedAndEstadoRegistro(Long idHuesped, EstadoRegistro estadoRegistro);
}
