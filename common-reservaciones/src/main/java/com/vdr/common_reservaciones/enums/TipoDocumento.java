package com.vdr.common_reservaciones.enums;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDocumento {

    INE(1L, "Credencial Electoral"),
    PASAPORTE(2L, "Pasaporte"),
    LICENCIA_CONDUCIR(3L, "Licencia de conducir"),
    CEDULA_PROFESIONAL(4L, "Cédula Profesional"),
    CARTILLA_MILITAR(5L, "Cartilla del Servicio Militar Nacional"),
    INAPAM(6L, "Credencial para personas adultas mayores");

    private final Long codigo;
    private final String descripcion;

    public static TipoDocumento fromCodigo(Long codigo) {
        if (codigo == null) {
            throw new IllegalArgumentException("El código no puede ser null");
        }

        for (TipoDocumento e : values()) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }

        throw new IllegalArgumentException("Código de documento no válido: " + codigo);
    }

    public static TipoDocumento fromDescripcion(String descripcion) {
        if (descripcion == null) {
            throw new IllegalArgumentException("La descripción no puede ser null");
        }

        String descripcionNormalizada = quitarAcentos(descripcion.trim());

        for (TipoDocumento e : values()) {
            String descripcionEnum = quitarAcentos(e.getDescripcion());

            if (descripcionEnum.equalsIgnoreCase(descripcionNormalizada)) {
                return e;
            }
        }

        throw new IllegalArgumentException("Descripción de documento no válida: " + descripcion);
    }

    private static String quitarAcentos(String s) {
        return s
                .replace("á", "a").replace("Á", "A")
                .replace("é", "e").replace("É", "E")
                .replace("í", "i").replace("Í", "I")
                .replace("ó", "o").replace("Ó", "O")
                .replace("ú", "u").replace("Ú", "U");
    }
}