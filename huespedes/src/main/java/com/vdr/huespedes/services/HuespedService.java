package com.vdr.huespedes.services;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.service.CrudService;

public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse>{

	HuespedResponse obtenerPorIdSinEstado(Long id);
}
