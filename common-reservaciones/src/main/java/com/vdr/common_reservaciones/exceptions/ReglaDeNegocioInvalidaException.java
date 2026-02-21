package com.vdr.common_reservaciones.exceptions;

public class ReglaDeNegocioInvalidaException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public ReglaDeNegocioInvalidaException(String mesagem) {
		super(mesagem);
	}
}
