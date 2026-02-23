package com.vdr.habitaciones.mapper;

import org.springframework.stereotype.Component;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.enums.EstadoHabitacion;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.enums.TipoHabitacion;
import com.vdr.common_reservaciones.mappers.CommonMapper;
import com.vdr.habitaciones.entities.Habitacion;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion>{

	@Override
	public HabitacionResponse entityToResponse(Habitacion entity) {
		if(entity == null) {return null;}
		
		
		return new HabitacionResponse(
					entity.getId(),
					entity.getNumeroHabitacion(),
					entity.getTipoHabitacion().getDescripcion(),
					entity.getPrecio(),
					entity.getCapacidad(),
					entity.getEstadoHabitacion().getDescripcion()
				);
	}

	@Override
	public Habitacion requestToEntity(HabitacionRequest request) {
		// TODO Auto-generated method stub
		return Habitacion.builder()
				.numeroHabitacion(request.numeroHabitacion())
				.tipoHabitacion(TipoHabitacion.fromCodigo(request.idTipoHabitacion()))
				.precio(request.precio())
				.capacidad(request.capacidad())
				.estadoHabitacion(EstadoHabitacion.fromCodigo(request.idEstadoHabitacion()))
				.estadoRegistro(EstadoRegistro.ACTIVO)
				.build();
	}

	@Override
	public Habitacion updateEntityFromRequest(HabitacionRequest request, Habitacion entity) {
		if(entity == null || request == null) return null;
		entity.setNumeroHabitacion(request.numeroHabitacion());
		entity.setTipoHabitacion(TipoHabitacion.fromCodigo(request.idTipoHabitacion()));
		entity.setPrecio(request.precio());
		entity.setCapacidad(request.capacidad());
		entity.setEstadoHabitacion(EstadoHabitacion.fromCodigo(request.idEstadoHabitacion()));
		return entity;
	}

}
