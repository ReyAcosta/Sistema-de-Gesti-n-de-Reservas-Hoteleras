package com.vdr.huespedes.mappers;

import org.springframework.stereotype.Component;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.enums.Nacionalidad;
import com.vdr.common_reservaciones.enums.TipoDocumento;
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
		                entity.getNombre(),
				        entity.getApellidoPaterno(),
				        entity.getApellidoMaterno(),
		                entity.getEmail(),
		                entity.getTelefono(),
		                entity.getTipoDocumento().getDescripcion(),
		                entity.getNacionalidad().getDescripcion());
        
	}
	
	@Override
	public Huesped requestToEntity(HuespedRequest request) {
		if (request == null) return null;

        return Huesped.builder()
                .nombre(request.nombre())
                .apellidoPaterno(request.apellidoPaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .email(normalizarCorreo(request.email()))
                .telefono(request.telefono())
                .tipoDocumento(TipoDocumento.fromCodigo(request.idDocumento()))
                .nacionalidad(Nacionalidad.fromCodigo(request.idNacionalidad()))
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
	}
	
	
	
	@Override
	public Huesped updateEntityFromRequest(HuespedRequest request, Huesped entity) {
		if (request == null || entity == null) return entity;

        entity.setNombre(request.nombre());
        entity.setApellidoPaterno(request.apellidoPaterno());
        entity.setApellidoMaterno(request.apellidoMaterno());
        entity.setEmail(normalizarCorreo(request.email()));
        entity.setTelefono(request.telefono());
        entity.setTipoDocumento(TipoDocumento.fromCodigo(request.idDocumento()));
        entity.setNacionalidad(Nacionalidad.fromCodigo(request.idNacionalidad()));

        return entity;
	}
	
	private String normalizarCorreo(String correo) {
		String correoNormalizado = correo.toLowerCase();
		
		correoNormalizado.replace("á", "a")
			  .replace("é", "e")
			  .replace("í", "i")
			  .replace("ó", "o")
			  .replace("ú", "u")
			  .replace("ü", "u")
			  .replace("ñ", "n");
		
		return correoNormalizado;
	}

}
