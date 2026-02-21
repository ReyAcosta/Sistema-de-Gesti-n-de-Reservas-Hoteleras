package com.vdr.common_reservaciones.dtos.data;

public record HuespedData(
		String nombre, 
		String apellidoPaterno,
		String apellidoMaterno,
		String email,
		String telefono, 
		String documento,
		String nacionalidad
		) {
	
}
