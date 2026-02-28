package com.vdr.common_reservaciones.enums;

import com.vdr.common_reservaciones.exceptions.ReglaDeNegocioInvalidaException;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoHabitacion {

    DISPONIBLE(1L, "Lista para asignarse"),
    OCUPADA(2L, "Asignada a una reserva"),
    LIMPIEZA(3L, "En limpieza"),
    MANTENIMIENTO(4L, "En reparación");

    private final Long codigo;
    private final String descripcion;
    
    public static EstadoHabitacion fromCodigo(Long codigo) {
        for (EstadoHabitacion e : values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Código de cita no válido: " + codigo);
    }

    public static EstadoHabitacion fromDescripcion(String descripcion) {
        for (EstadoHabitacion e : values()) {
            if (e.getDescripcion().equalsIgnoreCase(descripcion)) {
                return e;
            }
        }
        throw new IllegalArgumentException(
            "Descripción de la habitación no válida: " + descripcion
        );
    }
    
    
    public boolean puedeCambiarA(EstadoHabitacion nuevoEstado) {
	    return switch (this) {

	        case DISPONIBLE ->
	          		nuevoEstado == OCUPADA || nuevoEstado == MANTENIMIENTO || nuevoEstado == LIMPIEZA;
	        case OCUPADA ->
	                nuevoEstado == LIMPIEZA || nuevoEstado == MANTENIMIENTO;
	        case LIMPIEZA ->
	                nuevoEstado == DISPONIBLE;
	        case MANTENIMIENTO ->
            		nuevoEstado == LIMPIEZA;
	    };
	}
    
    
    public void validarTransicion(EstadoHabitacion nuevoEstado) {

	    if (this == nuevoEstado) {
	        throw new ReglaDeNegocioInvalidaException("La cita ya se encuentra en estado: " + this);
	    }

	    if (!puedeCambiarA(nuevoEstado)) {
	        throw new ReglaDeNegocioInvalidaException(
	                "No se puede cambiar el estado de " + this + " a " + nuevoEstado
	        );
	    }
	}
    
    
    
}
