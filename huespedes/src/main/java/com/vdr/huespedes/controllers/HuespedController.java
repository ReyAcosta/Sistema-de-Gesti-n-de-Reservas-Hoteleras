package com.vdr.huespedes.controllers;

import com.vdr.common_reservaciones.controllers.CommonController;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.huespedes.services.HuespedService;

public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService>{
	
	public HuespedController(HuespedService service) {
		super(service);
	}

}
