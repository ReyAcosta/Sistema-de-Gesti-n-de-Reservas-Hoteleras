package com.vdr.common_reservaciones.dtos.habitaciones;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HabitacionRequest(
		
		@NotNull(message = "El nuermo de habitacion es obligatorio")
	    @Min(value = 1, message = "El número de habitación debe ser mayor a 0")
	    Short numeroHabitacion,

	    @NotNull(message = "El id del tipo de habitación es obligatorio")
	    @Positive(message = "El id del estado de la habitacion debe de ser positivo")
	    Long idTipoHabitacion,

	    @NotNull(message = "El precio es obligatorio")
	    @Min(value= 1, message = "El precio debe ser mayor a 0")
		BigDecimal precio,

	    @NotNull(message = "La capacidad es requerida")
	    @Min(value = 1, message = "La capacidad mínima es 1")
	    Short capacidad,
	    
	    @NotNull(message = "El id es requerido")
	    @Positive(message = "El id del estado de la habitacion debe de ser positivo")
	    Long idEstadoHabitacion

	) {}
