package com.vdr.reservaciones.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vdr.common_reservaciones.dtos.data.HabitacionData;
import com.vdr.common_reservaciones.dtos.data.HuespedData;

public record ReservacionResponse(
	Long id, 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
	LocalDateTime fechaReserva,
	HabitacionData habitacion,
	HuespedData huesped,
	String estadoReserva
	
){}