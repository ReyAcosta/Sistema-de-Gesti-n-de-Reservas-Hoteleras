package com.vdr.reservaciones.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vdr.common_reservaciones.dtos.data.HabitacionData;
import com.vdr.common_reservaciones.dtos.data.HuespedData;

public record ReservacionResponse(
	Long id, 
	HabitacionData habitacion,
	HuespedData huesped,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
	LocalDateTime fechaReserva,
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
	LocalDateTime fechaInicio,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
	LocalDateTime fechaFin,
	String estadoReserva
	
){}