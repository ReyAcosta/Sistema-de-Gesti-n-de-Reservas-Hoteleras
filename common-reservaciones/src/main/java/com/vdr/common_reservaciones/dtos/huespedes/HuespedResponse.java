package com.vdr.common_reservaciones.dtos.huespedes;

import com.vdr.common_reservaciones.enums.TipoDocumento;

public record HuespedResponse(
		Long id, 
		String nombre, 
		String email,
		String telefono, 
		TipoDocumento tipoDocumento,
		String nacionalidad

	){}