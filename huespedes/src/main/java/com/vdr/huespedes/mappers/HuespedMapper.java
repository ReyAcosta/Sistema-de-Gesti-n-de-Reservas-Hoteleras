package com.vdr.huespedes.mappers;

import org.springframework.stereotype.Component;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.mappers.CommonMapper;
import com.vdr.huespedes.entities.Huesped;

@Component
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped>{
	
	@Override
	public HuespedResponse entityToResponse(Huesped entity) {
		if (entity == null) {
			 return null;
	     }
			
			 return new HuespedResponse(
		                entity.getId(),
		                String.join(" ", 
		                		entity.getNombre(),
				                entity.getApellidoPaterno(),
				                entity.getApellidoMaterno()),
		                entity.getEmail(),
		                entity.getTelefono(),
		                entity.getTipoDocumento(),
		                entity.getNacionalidad());
        
	}
	
	@Override
	public Huesped requestToEntity(HuespedRequest request) {
		if (request == null) return null;

        return Huesped.builder()
                .nombre(request.nombre())
                .apellidoPaterno(request.apellidoPaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .email(request.email())
                .telefono(request.telefono())
                .nacionalidad(request.nacionalidad())
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
	}
	
	@Override
	public Huesped updateEntityFromRequest(HuespedRequest request, Huesped entity) {
		if (request == null || entity == null) return entity;

        entity.setNombre(request.nombre());
        entity.setApellidoPaterno(request.apellidoPaterno());
        entity.setApellidoMaterno(request.apellidoMaterno());
        entity.setEmail(request.email());
        entity.setTelefono(request.telefono());
        entity.setNacionalidad(request.nacionalidad());

        return entity;
	}

}
