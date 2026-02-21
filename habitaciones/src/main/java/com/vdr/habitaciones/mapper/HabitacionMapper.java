package com.vdr.habitaciones.mapper;

import org.springframework.stereotype.Component;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.enums.EstadoHabitacion;
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
					entity.getTipoHabitacion(),
					entity.getPrecio(),
					entity.getCapacidad(),
					entity.getEstadoHabitacion()
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
				.build();
	}

	@Override
	public Habitacion updateEntityFromRequest(HabitacionRequest request, Habitacion entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
