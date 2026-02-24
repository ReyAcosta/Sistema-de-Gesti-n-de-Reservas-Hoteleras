package com.vdr.common_reservaciones.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="reservaciones-msv")
public interface ReservaClient {
	
	@GetMapping("/id-huesped/{idHuesped}/reservas-activas")
	Void huespedTieneReservasActivas(@PathVariable Long idHuesped);

    }
