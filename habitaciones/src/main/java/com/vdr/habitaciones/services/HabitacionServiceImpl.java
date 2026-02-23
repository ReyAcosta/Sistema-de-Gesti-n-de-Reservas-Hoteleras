package com.vdr.habitaciones.services;

import java.util.List;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.enums.EstadoHabitacion;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.exceptions.ReglaDeNegocioInvalidaException;
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
	@Transactional(readOnly = true)
	public HabitacionResponse obtenerPorIdSinEstado(Long id) {
		return habitacionMapper.entityToResponse(getHabitacionOrThrowSinEstado(id));
	}

	@Override
	public HabitacionResponse registrar(HabitacionRequest request) {
		log.info("Iniciadno registrar habitacion: {}", request);
		validarNumeroHabitacionUnico(request.numeroHabitacion());
		Habitacion habitacion = habitacionRepository.save(habitacionMapper.requestToEntity(request));
		
		return habitacionMapper.entityToResponse(habitacion);
	}

	@Override
	public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
		log.info("Iniciando actualizar habitacion con id: {}", id);
		Habitacion habitacion = getHabitacionOrThrow(id);
		validarNumeroHabitacionUnicoActualizar(request.numeroHabitacion(), id);
		habitacionMapper.updateEntityFromRequest(request, habitacion);
		
		
		return habitacionMapper.entityToResponse(habitacion);
	}
	
	@Override
	public HabitacionResponse actualizarEstadoHabitacion(Long idHabitacion, Long idEstadoHabitacion) {
		Habitacion habitacion = getHabitacionOrThrow(idEstadoHabitacion);
		EstadoHabitacion estado = EstadoHabitacion.fromCodigo(idEstadoHabitacion); 
		
		habitacion.setEstadoHabitacion(estado);
		
		return habitacionMapper.entityToResponse(habitacion);
	}

	@Override
	public void eliminar(Long id) {
		Habitacion habitacion = getHabitacionOrThrow(id);
		
		habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
	}
	
	/*-----------Metodos Privados----------*/
	
	 private Habitacion getHabitacionOrThrow(Long id){
	        log.info("Buscando habitacion con id: {}", id);
	        return  habitacionRepository.findByIdAndEstadoRegistro(id,EstadoRegistro.ACTIVO).orElseThrow(()->
	                new NoSuchElementException("Habitacion no encontrada con el id:" +id));
	    }
	 
	 private Habitacion getHabitacionOrThrowSinEstado(Long id) {
		 log.info("Buscando habitacion sin estado por id: {}", id);
		 return habitacionRepository.findById(id).orElseThrow(
				 ()-> new NoSuchElementException("Habitacion no encontrada con id: " + id));
	 }
	 
	 /*----------------Validar datos unicos registrar-----------*/
	private void validarNumeroHabitacionUnico(Short numHabitacion) {
		if(habitacionRepository.existsByNumeroHabitacionAndEstadoRegistro(numHabitacion, EstadoRegistro.ACTIVO)) {
			throw new ReglaDeNegocioInvalidaException("Ya existe un numero de habitacion con numero: "+ numHabitacion);
		}
	}
	
	 /*----------------Validar datos unicos actualizar-----------*/
		private void validarNumeroHabitacionUnicoActualizar(Short numHabitacion, Long id) {
			if(habitacionRepository.existsByNumeroHabitacionAndEstadoRegistroAndIdNot(numHabitacion, EstadoRegistro.ACTIVO, id)) {
				throw new ReglaDeNegocioInvalidaException("Ya existe un numero de habitacion con numero: "+ numHabitacion);
			}
		}
	
}
