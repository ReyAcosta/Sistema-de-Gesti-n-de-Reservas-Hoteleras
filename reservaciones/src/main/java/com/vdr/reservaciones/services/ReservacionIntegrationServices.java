package com.vdr.reservaciones.services;

import org.springframework.stereotype.Service;

import com.vdr.common_reservaciones.clients.HabitacionClient;
import com.vdr.common_reservaciones.clients.HuespedClient;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoHabitacion;
import com.vdr.common_reservaciones.exceptions.ReglaDeNegocioInvalidaException;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.entities.Reservacion;
import com.vdr.reservaciones.enums.EstadoReserva;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReservacionIntegrationServices {
	private final HabitacionClient habitacionClient;
	private final HuespedClient huespedClient;

	public void verificarCambiosHuespedHabitacionEnReserva(ReservacionRequest request, Reservacion reservacion) {
		if(!reservacion.getIdHuesped().equals(request.idHuesped())) {
			throw new IllegalArgumentException("No se puede modificar el usuario");
		}
		
		if(!reservacion.getIdHabitacion().equals(request.idHabitacion()) && 
				reservacion.getEstadoReserva().equals(EstadoReserva.EN_CURSO)) {
			throw new ReglaDeNegocioInvalidaException("No se puede cambiar de habitacion con la reserva en curso");
		}
		
			if(!reservacion.getIdHabitacion().equals(request.idHabitacion())) {
				habitacionClient.validarEstadoHabitacion(request.idHabitacion());
				habitacionClient.cambioHabitacion(reservacion.getIdHabitacion(),request.idHabitacion());
			}
		
	}
	
	
	public void cambioEstadoHabitacion(Long idHabitacion, EstadoHabitacion estadoHabitacion) {
		habitacionClient.actualizarEstadoHabitacion(idHabitacion, estadoHabitacion);
	}
	
	
	public void cambiarEstadoHabitacionSireservaEliminada(Long idHabitacion, EstadoReserva estadoReserva) {
		if(estadoReserva.equals(EstadoReserva.FINALIZADA)) {
			cambioEstadoHabitacion(idHabitacion, EstadoHabitacion.DISPONIBLE );
		}
	}
	
	public void validarEstadoHabitacion(Long idHabitacion) {
		habitacionClient.validarEstadoHabitacion(idHabitacion);
	}
	
	public HabitacionResponse obtenerHabitacionPorId(Long idHabitacion) {
		return habitacionClient.obtenerHabitacionPorId(idHabitacion);
	}
	
	public HabitacionResponse obtenerHabitacionPorIdSinEstado(Long idHabitacion) {
		return habitacionClient.obtenerPorIdSinEstado(idHabitacion);
	}
		
	
	/*--------------huesped--------------*/
	public HuespedResponse obtenerHuespedPorId(Long idHuesped) {
		return huespedClient.obtenerHuespedPorId(idHuesped);
	}
	
	public HuespedResponse obtenerHuespedPorIdSinEstado(Long idHuesped) {
		return huespedClient.obtenerPorIdSinEstado(idHuesped);
	}
		
}
