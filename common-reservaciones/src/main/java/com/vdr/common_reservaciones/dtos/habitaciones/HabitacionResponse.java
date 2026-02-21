package com.vdr.common_reservaciones.dtos.habitaciones;

import java.math.BigDecimal;

import com.vdr.common_reservaciones.enums.EstadoHabitacion;
import com.vdr.common_reservaciones.enums.TipoHabitacion;

public record HabitacionResponse(
		Long id, 
		Short numeroHabitacion,
		String tipoHabitacion, 
		BigDecimal precio, 
		Short capacidad, 
		String estadoHabitacion

	){}
