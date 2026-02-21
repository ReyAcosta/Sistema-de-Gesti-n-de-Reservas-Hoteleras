package com.vdr.common_reservaciones.dtos.habitaciones;

import com.vdr.common_reservaciones.enums.TipoHabitacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record HabitacionRequest(

	    @Min(value = 1, message = "El número de habitación debe ser mayor a 0")
	    Short numeroHabitacion,

	    @NotNull(message = "El tipo de habitación es obligatorio")
	    TipoHabitacion tipoHabitacion,

	    @Positive(message = "El precio debe ser mayor a 0")
	    Double precio,

	    @Min(value = 1, message = "La capacidad mínima es 1")
	    Short capacidad

	) {}
