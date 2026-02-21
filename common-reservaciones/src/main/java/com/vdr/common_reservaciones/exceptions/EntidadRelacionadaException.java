package com.vdr.common_reservaciones.exceptions;

public class EntidadRelacionadaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadRelacionadaException(String mensagem) {
        super(mensagem);
    }
}
