package com.vdr.reservaciones.services;

import com.vdr.common_reservaciones.service.CrudService;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.dtos.ReservacionResponse;

public interface ReservacionService extends CrudService<ReservacionRequest, ReservacionResponse>{
	
	ReservacionResponse actualizarEstadoReserva(Long idReserva, Long idEstadoReserva);
	
	void huespedTieneConsultasConfirmadasEnCurso(Long idHuesped);
	
}
