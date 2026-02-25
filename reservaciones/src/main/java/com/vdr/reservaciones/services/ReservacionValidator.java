package com.vdr.reservaciones.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.vdr.common_reservaciones.exceptions.ReglaDeNegocioInvalidaException;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.entities.Reservacion;
import com.vdr.reservaciones.enums.EstadoReserva;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public final class ReservacionValidator {
	
	static final Map<EstadoReserva, List<EstadoReserva>> cambiosDisponibles = Map.of(
			EstadoReserva.CONFIRMADA, List.of(EstadoReserva.EN_CURSO, EstadoReserva.CANCELADA),
			EstadoReserva.EN_CURSO, List.of(EstadoReserva.FINALIZADA)
	);
	
	public void verificarEstadoReserva(EstadoReserva estadoActual, EstadoReserva estadoNuevo) {
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
	
	
	private void verificarFechaInicioFechaFin(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		if(fechaInicio.isAfter(fechaFin)) {
			throw new IllegalArgumentException("La fecha de inicio no puede ser despue de la de fin");
		}
	}
	
	public void verificarCambiosEstadoReserva(ReservacionRequest request, Reservacion reservacion) {
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

}
