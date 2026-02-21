package com.vdr.common_reservaciones.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Rol{

    USER(1L, "Recepcionista"),
    ADMIN(2L, "Gerente");

    private final Long codigo;
    private final String descripcion;

    public static Rol fromDescripcion(String descripcion) {
        for (Rol e : values()) {
            if (e.getDescripcion().equalsIgnoreCase(descripcion)) {
                return e;
            }
        }
        throw new IllegalArgumentException(
            "Descripción del rol no válida: " + descripcion
        );
    }
}
