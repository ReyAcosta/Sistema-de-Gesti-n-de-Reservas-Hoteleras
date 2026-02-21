package com.vdr.common_reservaciones.dtos.data;

import java.math.BigDecimal;

public record HabitacionData(
		Short numeroHabitacion,
		String tipoHabitacion, 
		BigDecimal precio, 
		Short capacidad, 
		String estadoHabitacion) {

}
