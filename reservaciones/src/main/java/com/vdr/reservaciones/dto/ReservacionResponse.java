package com.vdr.reservaciones.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vdr.common_reservaciones.dtos.habitaciones.DatosHabitacion;
import com.vdr.common_reservaciones.dtos.huespedes.DatosHuesped;


public record ReservacionResponse(
		Long id, 
		DatosHuesped huesped,
		DatosHabitacion habitacion,
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
		LocalDateTime fechaReserva,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
		LocalDateTime fechaEntrada,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
		LocalDateTime fechaSalida,
		String estadoReserva
		) {}
