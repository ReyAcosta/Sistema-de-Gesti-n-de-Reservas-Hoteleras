package com.vdr.habitaciones.controller;

import org.springframework.web.bind.annotation.RestController;

import com.vdr.common_reservaciones.controllers.CommonController;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.habitaciones.services.HabitacionService;

@RestController
public class HabiacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService>{

	public HabiacionController(HabitacionService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}

}
