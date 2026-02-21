package com.vdr.common_reservaciones.dtos.habitaciones;


public record HabitacionResponse(
		Long id, 
		Short numeroHabitacion,
		String tipoHabitacion, 
		Double precio, 
		Short capacidad, 
		String estadoHabitacion

	){}
