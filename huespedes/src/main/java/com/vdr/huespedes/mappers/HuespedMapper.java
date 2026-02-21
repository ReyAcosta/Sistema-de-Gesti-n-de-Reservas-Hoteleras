package com.vdr.huespedes.mappers;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.mappers.CommonMapper;
import com.vdr.huespedes.entities.Huesped;

public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped>{
	
	@Override
	public HuespedResponse entityToResponse(Huesped entity) {
		
		return null;
	}
	
	@Override
	public Huesped requestToEntity(HuespedRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Huesped updateEntityFromRequest(HuespedRequest request, Huesped entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
