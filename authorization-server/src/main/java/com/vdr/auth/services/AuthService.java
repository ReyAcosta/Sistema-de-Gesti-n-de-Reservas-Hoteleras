package com.vdr.auth.services;

import com.vdr.auth.dtos.LoginRequest;
import com.vdr.auth.dtos.TokenResponse;

public interface AuthService {

    TokenResponse autenticar(LoginRequest request) throws Exception;
}

