package com.vdr.common_reservaciones.dtos.huespedes;


public record HuespedResponse(
		Long id, 
		String nombre, 
		String email,
		String telefono, 
		String documento,
		String nacionalidad

	){}