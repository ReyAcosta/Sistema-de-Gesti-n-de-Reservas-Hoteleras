package com.vdr.auth.services;

import java.util.Set;

import com.vdr.auth.dtos.UsuarioRequest;
import com.vdr.auth.dtos.UsuarioResponse;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}

