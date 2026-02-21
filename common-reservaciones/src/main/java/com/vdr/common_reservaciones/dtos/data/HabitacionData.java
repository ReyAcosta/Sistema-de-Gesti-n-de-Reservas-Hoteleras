package com.vdr.common_reservaciones.dtos.data;

public record HabitacionData(
		Short numeroHabitacion,
		String tipoHabitacion, 
		Double precio, 
		Short capacidad, 
		String estadoHabitacion) {

}
