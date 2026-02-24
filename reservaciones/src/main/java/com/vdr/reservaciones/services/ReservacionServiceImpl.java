package com.vdr.reservaciones.services;

import java.util.List;


import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vdr.common_reservaciones.clients.HabitacionClient;
import com.vdr.common_reservaciones.clients.HuespedClient;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.exceptions.EntidadRelacionadaException;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.dtos.ReservacionResponse;
import com.vdr.reservaciones.entities.Reservacion;
import com.vdr.reservaciones.enums.EstadoReserva;
import com.vdr.reservaciones.mapper.ReservacionMapper;
import com.vdr.reservaciones.repositories.ReservacionRepository;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReservacionServiceImpl implements ReservacionService{
	private final ReservacionRepository reservacionRepository;
	private final ReservacionMapper reservacionMapper;
	private final HuespedClient huespedClient;
	private final HabitacionClient habitacionClient;

	@Override
	@Transactional(readOnly = true)
	public List<ReservacionResponse> listar() {
		log.info("Listando reservaciones activas");
		return reservacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
				.map(reserva -> reservacionMapper.entityToResponse(
						reserva,
						getHuespedResponse(reserva.getIdHuesped()),
						getHabitacionResponse(reserva.getIdHabitacion()) )).toList() ;		
	}

	@Override
	@Transactional(readOnly = true)
	public ReservacionResponse obtenerPorId(Long id) {
		log.info("Obteniendo reservación con id: {}", id);
		Reservacion reservacion = getReservacionOrThrow(id);
		
		return reservacionMapper.entityToResponse(reservacion,
				getHuespedResponse(reservacion.getIdHuesped()),
				getHabitacionResponse(reservacion.getIdHabitacion()));
	}
	

	@Override
	
	public ReservacionResponse registrar(ReservacionRequest request) {
		log.info("Registrando nueva reservación");

        Reservacion reservacion = reservacionMapper.requestToEntity(request);

        Reservacion guardada = reservacionRepository.save(reservacion);

        return reservacionMapper.entityToResponse(guardada, getHuespedResponse(request.idHuesped()),getHabitacionResponse(request.idHabitacion()));
	}

	@Override
	public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
		 log.info("Actualizando reservación con id: {}", id);

	        Reservacion reservacion = getReservacionOrThrow(id);

	        reservacionMapper.updateEntityFromRequest(request, reservacion);

	        return reservacionMapper.entityToResponse(reservacion,
	        		getHuespedResponse(reservacion.getIdHuesped()),
	        		getHabitacionResponse(reservacion.getIdHabitacion()));
	}
	
	@Override
	public ReservacionResponse actualizarEstadoReserva(Long idReserva, Long idEstadoReserva) {
		Reservacion reserva = getReservacionOrThrow(idReserva);
		EstadoReserva estado = EstadoReserva.fromCodigo(idEstadoReserva);
		
		reserva.setEstadoReserva(estado);
		
		
		return reservacionMapper.entityToResponse(reserva,
				getHuespedResponse(reserva.getIdHuesped()),
				getHabitacionResponse(reserva.getIdHabitacion()));
	}

	@Override
	public void eliminar(Long id) {
		log.info("Eliminando reservación con id: {}", id);
        Reservacion reservacion = getReservacionOrThrow(id);
        
        reservacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
	}
	
	@Override
	public void huespedTieneConsultasConfirmadasEnCurso(Long idHuesped) {
		log.info("Validando reservaciones activas para el huesped id: {}", idHuesped);
		
		boolean tieneCitasActivas = reservacionRepository.existsByIdHuespedAndEstadoRegistroAndEstadoReserva(
				idHuesped, EstadoRegistro.ACTIVO, EstadoReserva.EN_CURSO);
		if(tieneCitasActivas) {
			throw new EntidadRelacionadaException("No se puede modificar el huesped porque tiene reservaciones." + EstadoReserva.EN_CURSO);
		}
				
	}
	
	/*-------------------Comienzan metodos privados-----------*/
	
	private Reservacion getReservacionOrThrow(Long id) {
		log.info("Buscando reservacion activa por id: {}",id);
		return reservacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(
				()-> new NoSuchElementException("No se econtro reservacion activa con id: " + id));
	}
	
	private Reservacion getReservacionOrThrowSinEstado(Long id) {
		log.info("Buscando reservacion activa por id: {}",id);
		return reservacionRepository.findById(id).orElseThrow(
				()-> new NoSuchElementException("No se econtro reservacion con id: " + id));
	}
	
	private HabitacionResponse getHabitacionResponse(Long id) {
		return habitacionClient.obtenerHabitacionPorId(id);
	}
	
	private HuespedResponse getHuespedResponse(Long id) {
		return huespedClient.obtenerHuespedPorId(id);
	}


 
	

}
