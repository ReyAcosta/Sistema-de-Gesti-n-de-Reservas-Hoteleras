package com.vdr.huespedes.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.huespedes.entities.Huesped;
import com.vdr.huespedes.mappers.HuespedMapper;
import com.vdr.huespedes.repositories.HuespedRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class HuespedServiceImpl implements HuespedService {
	private final HuespedRepository huespedRepository;
	private final HuespedMapper huespedMapper;
	
	@Override
	public List<HuespedResponse> listar() {
		log.info("Listando reservaciones activas");
        return huespedRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(huespedMapper::entityToResponse).toList();		
	}
	
	@Override
	public HuespedResponse obtenerPorId(Long id) {
        return huespedMapper.entityToResponse(getHuespedOrThrow(id));
	}
	
	@Override
	public HuespedResponse registrar(HuespedRequest request) {
		log.info("Registrando nuevo huesped");

        Huesped huesped = huespedMapper.requestToEntity(request);

        Huesped guardada = huespedRepository.save(huesped);

        return huespedMapper.entityToResponse(guardada);
	}
	
	@Override
	public HuespedResponse actualizar(HuespedRequest request, Long id) {
		 log.info("Actualizando reservación con id: {}", id);
	        Huesped huesped = getHuespedOrThrow(id);
	        
	        huespedMapper.updateEntityFromRequest(request, huesped);

	        return huespedMapper.entityToResponse(huesped);

	}
	
	@Override
	public void eliminar(Long id) {
		Huesped huesped = getHuespedOrThrow(id);
		
		log.info("Eliminando Huesped con id: {} ", id);
	    huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);	
		
	}
	
	/*---------------Comienzan metodos privados-----------*/
	
	/*---------Obtener huesped activo por id------------*/
	private Huesped getHuespedOrThrow(Long id) {
		log.info("Buscando huesped activo con id: " + id);
		return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(
				() -> new NoSuchElementException("No se encotro un huesped activo con el id: "+ id));
	}
	
	/*---------Obtener huesped sin importar estado por id------------*/
	private Huesped getHuespedOrThrowSinEstado(Long id) {
		log.info("Buscando huesped activo con id: " + id);
		return huespedRepository.findById(id).orElseThrow(
				() -> new NoSuchElementException("No se encotro un huesped con el id: "+ id));
	}
}
