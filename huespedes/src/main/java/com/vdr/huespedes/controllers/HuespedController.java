package com.vdr.huespedes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vdr.common_reservaciones.controllers.CommonController;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.huespedes.services.HuespedService;

@RestController
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService>{
	
	public HuespedController(HuespedService service) {
		super(service);
	}
	
	@GetMapping("/id-huesped/{id}")
	public ResponseEntity<HuespedResponse> obtenerPorIdSinEstado(@PathVariable Long id){
		return ResponseEntity.ok(service.obtenerPorIdSinEstado(id));
	}

}
