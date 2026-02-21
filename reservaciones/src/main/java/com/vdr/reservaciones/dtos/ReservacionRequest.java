package com.vdr.reservaciones.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public record ReservacionRequest(

	    @NotNull(message = "El huésped es obligatorio")
	    Long idHuesped,

	    @NotNull(message = "La habitación es obligatoria")
	    Long idHabitacion,

	    @NotNull(message = "La fecha de reserva es obligatoria")
	    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
	    LocalDateTime fechaReserva

	) {}
