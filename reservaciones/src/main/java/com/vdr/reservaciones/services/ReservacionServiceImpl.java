package com.vdr.reservaciones.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.exceptions.EntidadRelacionadaException;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.dtos.ReservacionResponse;
import com.vdr.reservaciones.entities.Reservacion;
import com.vdr.reservaciones.mapper.ReservacionMapper;
import com.vdr.reservaciones.repositories.ReservacionRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReservacionServiceImpl implements ReservacionService{
	private final ReservacionRepository reservacionRepository;
	private final ReservacionMapper reservacionMapper;

	@Override
	public List<ReservacionResponse> listar() {
		log.info("Listando reservaciones activas");
		return reservacionRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
				.map(reservacionMapper::entityToResponse).toList();		
		
	}

	@Override
	public ReservacionResponse obtenerPorId(Long id) {
		log.info("Obteniendo reservación con id: {}", id);
		return reservacionMapper.entityToResponse(getReservacionOrThrow(id));
	}

	@Override
	public ReservacionResponse registrar(ReservacionRequest request) {
		log.info("Registrando nueva reservación");

        Reservacion reservacion = reservacionMapper.requestToEntity(request);

        Reservacion guardada = reservacionRepository.save(reservacion);

        return reservacionMapper.entityToResponse(guardada);
	}

	@Override
	public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
		 log.info("Actualizando reservación con id: {}", id);

	        Reservacion reservacion = getReservacionOrThrow(id);

	        reservacionMapper.updateEntityFromRequest(request, reservacion);

	        return reservacionMapper.entityToResponse(reservacion);
	}

	@Override
	public void eliminar(Long id) {
		log.info("Eliminando reservación con id: {}", id);
        Reservacion reservacion = getReservacionOrThrow(id);
        
        reservacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
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

}
