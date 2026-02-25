package com.vdr.reservaciones.services;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdr.common_reservaciones.clients.HabitacionClient;
import com.vdr.common_reservaciones.clients.HuespedClient;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;

import com.vdr.common_reservaciones.exceptions.ReglaDeNegocioInvalidaException;

import com.vdr.common_reservaciones.exceptions.EntidadRelacionadaException;

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
		habitacionClient.validarEstadoHabitacion(request.idHabitacion());
		HabitacionResponse habitacion = habitacionClient.obtenerHabitacionPorId(request.idHabitacion());
		
        Reservacion reservacion = reservacionMapper.requestToEntity(request);
        Reservacion guardada = reservacionRepository.save(reservacion);
        
        habitacionClient.actualizarEstadoHabitacionSinRestriccion(reservacion.getIdHabitacion(), 2L);
        

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
		verificarEstadoReserva(reserva.getEstadoReserva(), estado);
		cambiarEstadoConformeReserva(estado, reserva.getIdHabitacion());
		
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
	
	@Override
	public void huespedTieneConsultasConfirmadasEnCurso(Long idHuesped) {
		log.info("Validando reservaciones activas para el huesped id: {}", idHuesped);
		
		boolean tieneCitasActivas = reservacionRepository.existsByIdHuespedAndEstadoRegistroAndEstadoReserva(
				idHuesped, EstadoRegistro.ACTIVO, EstadoReserva.EN_CURSO);
		if(tieneCitasActivas) {
			throw new EntidadRelacionadaException("No se puede modificar el huesped porque tiene reservaciones." + EstadoReserva.EN_CURSO);
		}
				
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

	
	
	
	
	private void verificarCambiosEstadoReserva(ReservacionRequest request, Reservacion reservacion) {
		verificarFechaInicioFechaFin(request.fechaInicio(), request.fechaFin());
		
		
		
		if(reservacion.getEstadoReserva().equals(EstadoReserva.FINALIZADA) ||
				reservacion.getEstadoReserva().equals(EstadoReserva.CANCELADA)) {
			throw new ReglaDeNegocioInvalidaException("No se puede modificar la la reservacion porque ya se encuentra en"
					+ "finalizada o cancelada");}
		
		if(!reservacion.getFechaFin().equals(request.fechaFin()) &&(
				!reservacion.getEstadoReserva().equals(EstadoReserva.CONFIRMADA) &&
				!reservacion.getEstadoReserva().equals(EstadoReserva.EN_CURSO)) ) {
				throw new ReglaDeNegocioInvalidaException("No se puede modificar la fecha salida  porque"
						+ "ya no esta en estado confirmada o en curso");
			}
		
		if(!reservacion.getFechaInicio().equals(request.fechaInicio()) &&
				!reservacion.getEstadoReserva().equals(EstadoReserva.CONFIRMADA)) {
			throw new ReglaDeNegocioInvalidaException("No se puede modificar la fecha de salida  porque"
					+ "ya no esta en estado confirmada");}
		
		
		
	}
	
	private void verificarCambiosHuespedHabitacionEnReserva(ReservacionRequest request, Reservacion reservacion) {
		if(!reservacion.getIdHuesped().equals(request.idHuesped())) {
			throw new IllegalArgumentException("No se puede modificar el usuario");
		}
			
			if(!reservacion.getIdHabitacion().equals(request.idHabitacion())) {
				habitacionClient.validarEstadoHabitacion(request.idHabitacion());
				habitacionClient.cambioHabitacion(reservacion.getIdHabitacion(),request.idHabitacion());
			}
		
	}
	
	private void verificarFechaInicioFechaFin(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		if(fechaInicio.isAfter(fechaFin)) {
			throw new IllegalArgumentException("La fecha de inicio no puede ser despue de la de fin");
		}
	}
	
	static final Map<EstadoReserva, List<EstadoReserva>> cambiosDisponibles = Map.of(
			EstadoReserva.CONFIRMADA, List.of(EstadoReserva.EN_CURSO, EstadoReserva.CANCELADA),
			EstadoReserva.EN_CURSO, List.of(EstadoReserva.FINALIZADA)
	);
	
	private void verificarEstadoReserva(EstadoReserva estadoActual, EstadoReserva estadoNuevo) {
		log.info("estado actual: {}, estado nuevo: {}", estadoActual, estadoNuevo);
		
		log.info("Clase estadoActual: {}", estadoActual.getClass().getName());
		log.info("Clase enum del map: {}", EstadoReserva.EN_CURSO.getClass().getName());
		
		if(estadoActual.equals(estadoNuevo)) {
			throw new ReglaDeNegocioInvalidaException("La reservacion ya se encuentra en estado " + estadoNuevo);
		}
		
		if(estadoActual.equals(EstadoReserva.FINALIZADA)|| estadoActual.equals(EstadoReserva.CANCELADA)){
			throw new ReglaDeNegocioInvalidaException("Cambio no permitido");
		}
		
		List<EstadoReserva> cambiosDis = cambiosDisponibles.getOrDefault(estadoActual, List.of());
		
		if(!cambiosDis.contains(estadoNuevo)) {
			throw new ReglaDeNegocioInvalidaException("No se puede pasar de " + estadoActual + " a "
					+ estadoNuevo);
		}
		
	}
	
	private void cambioEstadoHabitacion(Long idHabitacion, Long idEstadoHabitacion) {
		habitacionClient.actualizarEstadoHabitacionSinRestriccion(idHabitacion, idEstadoHabitacion);
	}
	
	private void cambiarEstadoConformeReserva(EstadoReserva estadoReserva,Long idHabitacion) {
		if(estadoReserva.equals(EstadoReserva.FINALIZADA)) {
			cambioEstadoHabitacion(idHabitacion, 1L);
		}
	}

}
