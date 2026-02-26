package com.vdr.auth.dtos;

import java.util.Set;



public record UsuarioResponse(
		String username,
		Set<String> roles
) {}

