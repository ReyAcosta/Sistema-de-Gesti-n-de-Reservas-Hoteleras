package com.vdr.reservaciones.services;

import java.time.LocalDateTime;
import java.util.List;


import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdr.common_reservaciones.clients.HabitacionClient;
import com.vdr.common_reservaciones.clients.HuespedClient;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.exceptions.ReglaDeNegocioInvalidaException;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.dtos.ReservacionResponse;
import com.vdr.reservaciones.entities.Reservacion;
import com.vdr.reservaciones.enums.EstadoReserva;
import com.vdr.reservaciones.mapper.ReservacionMapper;
import com.vdr.reservaciones.repositories.ReservacionRepository;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReservacionServiceImpl implements ReservacionService{
	private final ReservacionRepository reservacionRepository;
	private final ReservacionMapper reservacionMapper;
	private final HuespedClient huespedClient;
	private final HabitacionClient habitacionClient;

	@Override
	@Transactional(readOnly = true)
	public List<ReservacionResponse> listar() {
		log.info("Listando reservaciones activas");
		return reservacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
				.map(reserva -> reservacionMapper.entityToResponse(
						reserva,
						getHuespedResponse(reserva.getIdHuesped()),
						getHabitacionResponse(reserva.getIdHabitacion()) )).toList() ;		
	}

	@Override
	@Transactional(readOnly = true)
	public ReservacionResponse obtenerPorId(Long id) {
		log.info("Obteniendo reservación con id: {}", id);
		Reservacion reservacion = getReservacionOrThrow(id);
		
		return reservacionMapper.entityToResponse(reservacion,
				getHuespedResponse(reservacion.getIdHuesped()),
				getHabitacionResponse(reservacion.getIdHabitacion()));
	}
	

	@Override
	
	public ReservacionResponse registrar(ReservacionRequest request) {
		log.info("Registrando nueva reservación");
		HuespedResponse huesped =getHuespedResponse(request.idHuesped());
		HabitacionResponse habitacion =verificarHabitacionDisponible(request.idHabitacion());
		

        Reservacion reservacion = reservacionMapper.requestToEntity(request);

        Reservacion guardada = reservacionRepository.save(reservacion);

        return reservacionMapper.entityToResponse(guardada, huesped, habitacion);
	}

	@Override
	public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
		 log.info("Actualizando reservación con id: {}", id);

	        Reservacion reservacion = getReservacionOrThrow(id);
	        
	        verificarCambiosHuespedHabitacionEnReserva(request, reservacion);
	        verificarCambiosEstadoReserva(request, reservacion);

	        reservacionMapper.updateEntityFromRequest(request, reservacion);

	        return reservacionMapper.entityToResponse(reservacion,
	        		getHuespedResponse(reservacion.getIdHuesped()),
	        		getHabitacionResponse(reservacion.getIdHabitacion()));
	}
	
	@Override
	public ReservacionResponse actualizarEstadoReserva(Long idReserva, Long idEstadoReserva) {
		Reservacion reserva = getReservacionOrThrow(idReserva);
		EstadoReserva estado = EstadoReserva.fromCodigo(idEstadoReserva);
		
		
		reserva.setEstadoReserva(estado);
		
		
		return reservacionMapper.entityToResponse(reserva,
				getHuespedResponse(reserva.getIdHuesped()),
				getHabitacionResponse(reserva.getIdHabitacion()));
	}

	@Override
	public void eliminar(Long id) {
		log.info("Eliminando reservación con id: {}", id);
        Reservacion reservacion = getReservacionOrThrow(id);
        
        reservacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
	}
	
	/*-------------------Comienzan metodos privados-----------*/
	
	private Reservacion getReservacionOrThrow(Long id) {
		log.info("Buscando reservacion activa por id: {}",id);
		return reservacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(
				()-> new NoSuchElementException("No se econtro reservacion activa con id: " + id));
	}
	
	private Reservacion getReservacionOrThrowSinEstado(Long id) {
		log.info("Buscando reservacion activa por id: {}",id);
		return reservacionRepository.findById(id).orElseThrow(
				()-> new NoSuchElementException("No se econtro reservacion con id: " + id));
	}
	
	private HabitacionResponse getHabitacionResponse(Long id) {
		return habitacionClient.obtenerHabitacionPorId(id);
	}
	
	private HuespedResponse getHuespedResponse(Long id) {
		return huespedClient.obtenerHuespedPorId(id);
	}
	
	private HabitacionResponse verificarHabitacionDisponible(Long idHabitacion) {
		HabitacionResponse habitacion= getHabitacionResponse(idHabitacion);
		return habitacion;
	}
	
	private void actualizarEstadoHabitacionEnregistro() {}
	
	
	private void verificarCambiosEstadoReserva(ReservacionRequest request, Reservacion reservacion) {
		verificarFechaInicioFechaFin(request.fechaInicio(), request.fechaFin());
		
		if(reservacion.getEstadoReserva().equals(EstadoReserva.FINALIZADA) ||
				reservacion.getEstadoReserva().equals(EstadoReserva.CANCELADA)) {
			throw new ReglaDeNegocioInvalidaException("No se puede modificar la fecha de salida  porque"
					+ "ya no esta en estado confirmada");
		}
		
		if(!reservacion.getFechaInicio().equals(request.fechaInicio()) &&
				!reservacion.getEstadoReserva().equals(EstadoReserva.CONFIRMADA)) {
			throw new ReglaDeNegocioInvalidaException("No se puede modificar la fecha de salida  porque"
					+ "ya no esta en estado confirmada");
		}
		
		if(!reservacion.getFechaFin().equals(request.fechaFin()) &&(
			!reservacion.getEstadoReserva().equals(EstadoReserva.CONFIRMADA) &&
			!reservacion.getEstadoReserva().equals(EstadoReserva.EN_CURSO)) ) {
			throw new ReglaDeNegocioInvalidaException("No se puede modificar la fecha salida  porque"
					+ "ya no esta en estado confirmada o en curso");
		}
		
	}
	
	private void verificarCambiosHuespedHabitacionEnReserva(ReservacionRequest request, Reservacion reservacion) {
		if(!reservacion.getIdHuesped().equals(request.idHuesped()) ||
				!reservacion.getIdHuesped().equals(request.idHuesped())) {
			throw new IllegalArgumentException("No se puede modificar el usuario y habitacion");
		}
	}
	
	private void verificarFechaInicioFechaFin(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		if(fechaInicio.isAfter(fechaFin)) {
			throw new IllegalArgumentException("La fecha de inicio no puede ser despue de la de fin");
		}
	}

}
