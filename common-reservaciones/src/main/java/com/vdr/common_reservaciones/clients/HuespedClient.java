package com.vdr.common_reservaciones.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;

@FeignClient(name = "huespedes-msv")
public interface HuespedClient {
	
	@GetMapping("/{id}")
	HuespedResponse obtenerHuespedPorId(@PathVariable Long id);
}
