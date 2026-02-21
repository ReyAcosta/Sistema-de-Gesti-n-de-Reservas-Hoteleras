package com.vdr.reservaciones.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.reservaciones.dto.ReservacionRequest;
import com.vdr.reservaciones.dto.ReservacionResponse;
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
public class ReservacionImple implements ReservacionService{
	private final ReservacionRepository reservacionRepository;
	private final ReservacionMapper reservacionMapper;

	@Override
	public List<ReservacionResponse> listar() {
		log.info("Listando reservaciones activas");

        return reservacionRepository
                .findByEstadoRegistro(EstadoRegistro.ACTIVO)
                .stream()
                .map(reservacionMapper::entityToResponse)
                .toList();
	}

	@Override
	public ReservacionResponse obtenerPorId(Long id) {
		log.info("Obteniendo reservación con id: {}", id);

        Reservacion reservacion = reservacionRepository
                .findById(id)
                .filter(r -> r.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .orElseThrow(() ->
                        new IllegalArgumentException("Reservación no encontrada"));

        return reservacionMapper.entityToResponse(reservacion);
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

	        Reservacion reservacion = reservacionRepository
	                .findById(id)
	                .filter(r -> r.getEstadoRegistro() == EstadoRegistro.ACTIVO)
	                .orElseThrow(() ->
	                        new IllegalArgumentException("Reservación no encontrada"));

	        reservacionMapper.updateEntityFromRequest(request, reservacion);

	        Reservacion actualizada = reservacionRepository.save(reservacion);

	        return reservacionMapper.entityToResponse(actualizada);
	}

	@Override
	public void eliminar(Long id) {
		log.info("Eliminando reservación con id: {}", id);

        Reservacion reservacion = reservacionRepository
                .findById(id)
                .filter(r -> r.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .orElseThrow(() ->
                        new IllegalArgumentException("Reservación no encontrada"));

        reservacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);

        reservacionRepository.save(reservacion);
		
	}

}
