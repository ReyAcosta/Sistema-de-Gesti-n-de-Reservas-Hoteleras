package com.vdr.habitaciones.services;

import java.util.List;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.habitaciones.entities.Habitacion;
import com.vdr.habitaciones.mapper.HabitacionMapper;
import com.vdr.habitaciones.repository.HabitacionRepository;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class HabitacionServiceImpl implements HabitacionService{
	private final HabitacionRepository habitacionRepository;
	private final HabitacionMapper habitacionMapper;
	
	
	@Override
	@Transactional(readOnly = true)
	public List<HabitacionResponse> listar() {
		// TODO Auto-generated method stub
		log.info("Iniciando listar habitaciones activas");
		return habitacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
						.stream().map(habitacionMapper::entityToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public HabitacionResponse obtenerPorId(Long id) {
		return habitacionMapper.entityToResponse(getHabitacionOrThrow(id));
	}

	@Override
	public HabitacionResponse registrar(HabitacionRequest request) {
		log.info("Iniciadno registrar habitacion: {}", request);
		Habitacion habitacion = habitacionRepository.save(habitacionMapper.requestToEntity(request));
		
		return habitacionMapper.entityToResponse(habitacion);
	}

	@Override
	public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
		log.info("Iniciando actualizar habitacion con id: {}", id);
		Habitacion habitacion = getHabitacionOrThrow(id);
		habitacionMapper.updateEntityFromRequest(request, habitacion);
		
		
		return habitacionMapper.entityToResponse(habitacion);
	}

	@Override
	public void eliminar(Long id) {
		Habitacion habitacion = getHabitacionOrThrow(id);
		
		habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
	}
	
	 private Habitacion getHabitacionOrThrow(Long id){
	        log.info("Buscando habitacion con id: {}", id);
	        return  habitacionRepository.findByIdAndEstadoRegistro(id,EstadoRegistro.ACTIVO).orElseThrow(()->
	                new NoSuchElementException("Habitacion no encontrada con el id:" +id));
	    }
	
}
