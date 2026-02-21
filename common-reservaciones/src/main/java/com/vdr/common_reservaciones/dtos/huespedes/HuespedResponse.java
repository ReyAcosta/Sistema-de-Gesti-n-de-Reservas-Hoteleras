package com.vdr.common_reservaciones.dtos.huespedes;

import com.vdr.common_reservaciones.enums.EstadoRegistro;

public record HuespedResponse(
		Long id, 
		String nombre, 
		String apellidoPaterno,
		String apellidoMaterno,
		String email,
		String telefono, 
		String documento,
		Short nacionalidad,
		EstadoRegistro estadoRegistro

	){}