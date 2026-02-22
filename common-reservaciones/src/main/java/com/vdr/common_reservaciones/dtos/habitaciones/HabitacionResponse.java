package com.vdr.common_reservaciones.dtos.habitaciones;

import java.math.BigDecimal;



public record HabitacionResponse(
		Long id, 
		Short numeroHabitacion,
		String tipoHabitacion, 
		BigDecimal precio, 
		Short capacidad, 
		String estadoHabitacion

	){}
