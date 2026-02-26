package com.vdr.common_reservaciones.dtos.huespedes;


public record HuespedResponse(
		Long id, 
		String nombre, 
		String apellidoMaterno,
		String apellidoPaterno,
		String email,
		String telefono, 
		String tipoDocumento,
		String nacionalidad

	){}