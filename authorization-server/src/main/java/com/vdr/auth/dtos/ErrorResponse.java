package com.vdr.auth.dtos;

public record ErrorResponse(
        int codigo,
        String mensaje
) { }

