package com.vdr.reservaciones.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoReserva {
          CONFIRMADA(1L, "Reserva creada"),
<<<<<<< Updated upstream
          EN_CURSO(2L, "Check-in realizado"),
          FINALIZADA(3L, "Check-out realizado"),
=======
   	  EN_CURSO(2L, "Check-in realizado"),
	  FINALIZADA(3L, "Check-out realizado"),
>>>>>>> Stashed changes
          CANCELADA(4L, "Reserva cancelada");

	    private final Long codigo;
	    private final String descripcion;

	    public static EstadoReserva fromCodigo(Long codigo) {
 	       for (EstadoReserva e : values()) {
	            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
  	      throw new IllegalArgumentException("Código no válido: " + codigo);
    }

    public static EstadoReserva fromDescripcion(String descripcion) {
        for (EstadoReserva e : values()) {
            if (e.getDescripcion().equalsIgnoreCase(descripcion)) {
                return e;
            }
        }
<<<<<<< Updated upstream
        throw new IllegalArgumentException( "Descripción no válida: " + descripcion)  ;
    }
}
=======
        throw new IllegalArgumentException( "Descripción no válida: " + descripcion);
    }
}
>>>>>>> Stashed changes
