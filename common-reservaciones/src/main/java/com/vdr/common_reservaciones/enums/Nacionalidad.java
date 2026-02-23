package com.vdr.common_reservaciones.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Nacionalidad {
	MEXICO(1L,"Mexicana"),
	ESTADOSUNIDOS(2L, "Estadounidense"),
	COLOMBIA(3L, "Colombiana"),
	JAPON(4L,"Japonesa"),
	CUBA(5L, "Cubana"),
	BRASIL(6L, "Brasileira"),
	CHILE(7L,"Chilena"),
	ARGENTINA(8L,"Argentina");
	
	private final Long codigo;
	private final String descripcion;
	
	public static Nacionalidad fromCodigo(Long codigo) {
		for(Nacionalidad nacionalidad : values()) {
			if(nacionalidad.getCodigo().equals(codigo)){
				return nacionalidad;
			}
		}
	throw new IllegalArgumentException("No se encontro nacionalidad con codigo: " + codigo);
	}
	
}
