package com.vdr.common_reservaciones.dtos.habitaciones;

import com.vdr.common_reservaciones.enums.EstadoHabitacion;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.enums.TipoHabitacion;

public record HabitacionResponse(
		Long id, 
		Short numeroHabitacion,
		TipoHabitacion tipoHabitacion, 
		Double precio, 
		Short capacidad, 
		EstadoHabitacion estadoHabitacion,
		EstadoRegistro estadoRegistro

	){}
