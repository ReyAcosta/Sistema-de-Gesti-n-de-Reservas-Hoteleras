package com.vdr.reservaciones.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.vdr.common_reservaciones.controllers.CommonController;
import com.vdr.reservaciones.dto.ReservacionRequest;
import com.vdr.reservaciones.dto.ReservacionResponse;
import com.vdr.reservaciones.services.ReservacionService;

@RestController
public class ReservacionController extends CommonController<ReservacionRequest, ReservacionResponse, ReservacionService> {
	public ReservacionController(ReservacionService service) {
		super(service);
	}
}
