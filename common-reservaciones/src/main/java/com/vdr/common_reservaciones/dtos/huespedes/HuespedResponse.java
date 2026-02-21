package com.vdr.common_reservaciones.dtos.huespedes;


public record HuespedResponse(
		Long id, 
		String nombre, 
		String apellidoPaterno,
		String apellidoMaterno,
		String email,
		String telefono, 
		String documento,
		String nacionalidad


	){}