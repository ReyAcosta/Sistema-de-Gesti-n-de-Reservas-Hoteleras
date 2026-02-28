package com.vdr.reservaciones.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vdr.common_reservaciones.dtos.data.HabitacionData;
import com.vdr.common_reservaciones.dtos.data.HuespedData;

public record ReservacionResponse(
	Long id, 
	HabitacionData habitacion,
	HuespedData huesped,
	LocalDateTime fechaReserva,
	LocalDateTime fechaInicio,	
	LocalDateTime fechaFin,
	String estadoReserva
	
){}