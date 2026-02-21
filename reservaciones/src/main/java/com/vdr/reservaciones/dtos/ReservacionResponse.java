package com.vdr.reservaciones.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.enums.EstadoReserva;

public record ReservacionResponse(
	Long id, 
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern= "dd/MM/yyyy HH:mm")
	LocalDateTime fechaReserva,
	EstadoReserva reserva,
	EstadoRegistro registro
){}
