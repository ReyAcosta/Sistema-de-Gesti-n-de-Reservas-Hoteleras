package com.vdr.common_reservaciones.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public enum TipoHabitacion{
        INDIVIDUAL(1L, "Habitación Individual"),
        DOBLE(2L, "Habitación Doble"),
        TRIPLE(3L, "Habitación Triple");

    	private final Long codigo;
	private final String descripcion;
	
	public static TipoHabitacion fromCodigo(Long codigo) {
        for (TipoHabitacion e : values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Código de cita no válido: " + codigo);
    }
	
	public static TipoHabitacion fromDescripcion(String descripcion) {
        for (TipoHabitacion e : values()) {
            if (e.descripcion.equalsIgnoreCase(descripcion)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Descripción del Tipo de Reserva no válida: " + descripcion);
    }
}


