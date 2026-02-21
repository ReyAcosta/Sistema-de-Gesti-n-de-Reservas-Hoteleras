package com.vdr.habitaciones.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.habitaciones.entities.Habitacion;
import com.vdr.habitaciones.mapper.HabitacionMapper;
import com.vdr.habitaciones.repository.HabitacionRepository;

import jakarta.transaction.Transactional;
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
	public List<HabitacionResponse> listar() {
		// TODO Auto-generated method stub
		return habitacionRepository.findAll()
						.stream().map(Habitacion -> habitacionMapper.entityToResponse(Habitacion)).toList();
	}

	@Override
	@Transactional
	public HabitacionResponse obtenerPorId(Long id) {
		// TODO Auto-generated method stub
		return habitacionMapper.entityToResponse(getHabitacionOrThrow(id));
	}

	@Override
	public HabitacionResponse registrar(HabitacionRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminar(Long id) {
		Habitacion habitacion = getHabitacionOrThrow(id);
		//verificar si no tiene reservas activas
		habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
	}
	
	 private Habitacion getHabitacionOrThrow(Long id){
	        log.info("buscando paciente con id: {}", id);
	        return  habitacionRepository.findByIdAndEstadoRegistro(id,EstadoRegistro.ACTIVO).orElseThrow(()->
	                new NoSuchElementException("paciente no encontrado con el id:" +id));
	    }
	
}
