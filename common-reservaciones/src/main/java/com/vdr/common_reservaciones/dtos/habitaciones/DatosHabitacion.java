package com.vdr.common_reservaciones.dtos.habitaciones;

public record DatosHabitacion(
		Short numeroHabitacion,
		String tipoHabitacion,
		Double precio,
		String estadoRegistro
		) {}
