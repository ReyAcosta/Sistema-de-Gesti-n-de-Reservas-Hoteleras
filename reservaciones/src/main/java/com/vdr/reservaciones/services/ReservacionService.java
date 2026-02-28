package com.vdr.reservaciones.services;

import java.util.List;

import com.vdr.common_reservaciones.service.CrudService;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.dtos.ReservacionResponse;

public interface ReservacionService extends CrudService<ReservacionRequest, ReservacionResponse>{
	
	ReservacionResponse actualizarEstadoReserva(Long idReserva, Long idEstadoReserva);
	
	List<ReservacionResponse> listarEliminadas();
	
	ReservacionResponse obtenerPorIdSinEstado(Long id);
	
	void huespedTieneConsultasConfirmadasEnCurso(Long idHuesped);
	
	boolean habitacionesTieneReservacionesActivas(Long idHabitacion);
	
	void eliminarReservacionSiHuespedEliminado(Long idHuesped);
	
}
