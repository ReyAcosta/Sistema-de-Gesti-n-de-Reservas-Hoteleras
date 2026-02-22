package com.vdr.reservaciones.mapper;

import org.springframework.stereotype.Component;

import com.vdr.common_reservaciones.dtos.data.HabitacionData;
import com.vdr.common_reservaciones.dtos.data.HuespedData;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.mappers.CommonMapper;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.dtos.ReservacionResponse;
import com.vdr.reservaciones.entities.Reservacion;
import com.vdr.reservaciones.enums.EstadoReserva;

@Component
public class ReservacionMapper implements CommonMapper<ReservacionRequest, ReservacionResponse, Reservacion >{

	@Override
	public ReservacionResponse entityToResponse(Reservacion entity) {
		if(entity == null) return null;
		
		
		return new ReservacionResponse(
				entity.getId(),
				null,
				null,
				entity.getFechaReserva(),
				entity.getFechaInicio(),
				entity.getFechaFin(),
				entity.getEstadoReserva().getDescripcion());
	}
	
	public ReservacionResponse entityToResponse(Reservacion entity, HuespedResponse huesped, HabitacionResponse habitacion) {
		if(entity == null) return null;
		
		return new ReservacionResponse(
				entity.getId(),
				datosHabitacionFromHabitacionResponse(habitacion),
				datosHuespedFromHuespedResponse(huesped),
				entity.getFechaReserva(),
				entity.getFechaInicio(),
				entity.getFechaFin(),
				entity.getEstadoReserva().getDescripcion());
	}

	@Override
	public Reservacion requestToEntity(ReservacionRequest request) {
		if (request == null) return null;

        return Reservacion.builder()
                .idHuesped(request.idHuesped())
                .idHabitacion(request.idHabitacion())
                .fechaInicio(request.fechaInicio())
                .fechaFin(request.fechaFin())
                .estadoReserva(EstadoReserva.CONFIRMADA)
				.estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
	}

	@Override
	public Reservacion updateEntityFromRequest(ReservacionRequest request, Reservacion entity) {
		 if (request == null || entity == null) return entity;

	        entity.setIdHuesped(request.idHuesped());
	        entity.setIdHabitacion(request.idHabitacion());
	        entity.setFechaInicio(request.fechaInicio());
	        entity.setFechaFin(request.fechaFin());

	        return entity;
	}
	public Reservacion updateEntityFromRequest(ReservacionRequest request, Reservacion entity, EstadoReserva estadoReserva) {
		if(request == null || request == null) return null;
		
		updateEntityFromRequest(request, entity);
		entity.setEstadoReserva(estadoReserva);
		return entity; 
	}
	private HuespedData datosHuespedFromHuespedResponse(HuespedResponse huesped) {
		if (huesped == null) return null;
		
		return new HuespedData(
				huesped.nombre(),
			    huesped.email(),
			    huesped.telefono(),
			    huesped.documento(),
			    huesped.nacionalidad()
				);
	}
	private HabitacionData datosHabitacionFromHabitacionResponse(HabitacionResponse habitacion) {
		
		 if (habitacion == null) return null;

		    return new HabitacionData(
		    		habitacion.numeroHabitacion(),
		    	    habitacion.tipoHabitacion(),
		    	    habitacion.precio(),
		    	    habitacion.capacidad(),
		    	    habitacion.estadoHabitacion()
		        );
		    
}
	
	
	
	

}

