package com.vdr.huespedes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.exceptions.EntidadRelacionadaException;
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

        return huespedRepository
                .findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(huespedMapper::entityToResponse)
                .toList();
		
	}
	
	@Override
	public HuespedResponse obtenerPorId(Long id) {
		log.info("Obteniendo reservación con id: {}", id);

        Huesped huesped = huespedRepository
                .findById(id)
                .filter(r -> r.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .orElseThrow(() ->
                        new EntidadRelacionadaException("Huesped no encontrada"));

        return huespedMapper.entityToResponse(huesped);
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

	        Huesped huesped= huespedRepository
	                .findById(id)
	                .filter(r -> r.getEstadoRegistro() == EstadoRegistro.ACTIVO)
	                .orElseThrow(() ->
	                        new EntidadRelacionadaException("Huesped no encontrada"));

	        huespedMapper.updateEntityFromRequest(request, huesped);

	        Huesped actualizada = huespedRepository.save(huesped);

	        return huespedMapper.entityToResponse(actualizada);

	}
	
	@Override
	public void eliminar(Long id) {
		log.info("Eliminando Huesped con id: {} ", id);
		 Huesped huesped = huespedRepository
				 .findById(id)
				 .filter(r -> r.getEstadoRegistro() == EstadoRegistro.ACTIVO)
	                .orElseThrow(() ->
	                        new EntidadRelacionadaException("Reservación no encontrada"));

	        huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);

	        huespedRepository.save(huesped);
			
		
	}
}
