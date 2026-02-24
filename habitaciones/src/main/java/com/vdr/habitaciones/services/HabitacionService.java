package com.vdr.habitaciones.services;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.service.CrudService;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse> {
	HabitacionResponse obtenerPorIdSinEstado(Long id);
	
	HabitacionResponse actualizarEstadoHabitacion(Long idHabitacion, Long idEstadoHabitacion); 
	
	void validarEstadoHabitacion(Long idHabitacion);
}
