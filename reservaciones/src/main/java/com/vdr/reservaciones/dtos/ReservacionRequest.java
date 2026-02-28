package com.vdr.reservaciones.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservacionRequest(

	    @NotNull(message = "El huésped es obligatorio")
	    @Positive(message = "El id del huesped debe de ser positivo")
	    Long idHuesped,

	    @NotNull(message = "La habitación es obligatoria")
	    @Positive(message = "El id de la habitacion debe de ser positivo")
	    Long idHabitacion,

	    @NotNull(message = "La fecha de reserva es obligatoria")
	   
	    LocalDateTime fechaInicio,
	    
	    @NotNull(message = "La fecha de reserva es obligatoria")
	    
	    LocalDateTime fechaFin
	    
	) {}
