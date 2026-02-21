package com.vdr.reservaciones.dto;

import java.time.LocalDateTime;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.vdr.common_reservaciones.dtos.data.HabitacionData;
import com.vdr.common_reservaciones.dtos.data.HuespedData;




public record ReservacionResponse(
		Long id, 
		HuespedData huesped,
		HabitacionData habitacion,
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
		LocalDateTime fechaReserva,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
		LocalDateTime fechaEntrada,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
		LocalDateTime fechaSalida,
		String estadoReserva
		) {}
