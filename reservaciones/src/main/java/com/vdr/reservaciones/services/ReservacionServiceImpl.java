package com.vdr.reservaciones.services;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoHabitacion;
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
	private final ReservacionIntegrationServices servicesClients;
	private final ReservacionValidator validar;
	
	
/*---------------------------listar reservaciones----------------------------------*/
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
	public List<ReservacionResponse> listarEliminadas() {
		return reservacionRepository.findByEstadoRegistro(EstadoRegistro.ELIMINADO).stream()
				.map(reserva -> reservacionMapper.entityToResponse(
						reserva,
						getHuespedResponseSinEstado(reserva.getIdHuesped()),
						getHabitacionResponseSinEstado(reserva.getIdHabitacion()) )).toList() ;		
	}

/*-----------------------------obtener por id--------------------*/
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
	@Transactional(readOnly = true)
	public ReservacionResponse obtenerPorIdSinEstado(Long id) {
		Reservacion reservacion = reservacionRepository.findById(id).orElseThrow(
				()-> new NoSuchElementException("No se econtro reservacion con id: " + id));
		
		return reservacionMapper.entityToResponse(reservacion,
				getHuespedResponseSinEstado(reservacion.getIdHuesped()),
				getHabitacionResponseSinEstado(reservacion.getIdHabitacion()));
	}	
	
/*------------actualizar-------------------*/
	@Override
	public ReservacionResponse registrar(ReservacionRequest request) {
		log.info("Registrando nueva reservación");
		HuespedResponse huesped =getHuespedResponse(request.idHuesped());
		HabitacionResponse habitacion = servicesClients.obtenerHabitacionPorId(request.idHabitacion());
		if(reservacionRepository.existsByIdHuespedAndEstadoRegistro(huesped.id(), EstadoRegistro.ACTIVO)) {
			throw new IllegalArgumentException("El huesped ya tiene reservacion activa");
		}
		servicesClients.validarEstadoHabitacion(habitacion.id());
		validar.verificarFechaInicioFechaFin(request.fechaInicio(), request.fechaFin());
//		verificarSiHorarioCruzado(request);
		
        Reservacion reservacion = reservacionMapper.requestToEntity(request);
        Reservacion guardada = reservacionRepository.save(reservacion);
        
        servicesClients.cambioEstadoHabitacion(reservacion.getIdHabitacion(), EstadoHabitacion.OCUPADA);
        

        return reservacionMapper.entityToResponse(guardada, huesped, habitacion);
	}
	

	@Override
	public ReservacionResponse actualizar(ReservacionRequest request, Long id) {
		 log.info("Actualizando reservación con id: {}", id);

	        Reservacion reservacion = getReservacionOrThrow(id);
	        
	        validar.verificarCambiosEstadoReserva(request, reservacion);
	        servicesClients.verificarCambiosHuespedHabitacionEnReserva(request, reservacion);
//	        verificarSiHorarioCruzado(request);
	        
	        reservacionMapper.updateEntityFromRequest(request, reservacion);

	        return reservacionMapper.entityToResponse(reservacion,
	        		getHuespedResponse(reservacion.getIdHuesped()),
	        		getHabitacionResponse(reservacion.getIdHabitacion()));
	}
	
	
	
	@Override
	public ReservacionResponse actualizarEstadoReserva(Long idReserva, Long idEstadoReserva) {
		Reservacion reserva = getReservacionOrThrow(idReserva);
		EstadoReserva estado = EstadoReserva.fromCodigo(idEstadoReserva);
		validar.verificarEstadoReserva(reserva.getEstadoReserva(), estado);
		servicesClients.cambiarEstadoHabitacionSireservaEliminada(reserva.getIdHabitacion(),estado);
		
		reserva.setEstadoReserva(estado);
		return reservacionMapper.entityToResponse(reserva,
				getHuespedResponse(reserva.getIdHuesped()),
				getHabitacionResponse(reserva.getIdHabitacion()));
	}
	
/*---------------------------Eliminar-------------------------------*/
	@Override
	public void eliminar(Long id) {
		log.info("Eliminando reservación con id: {}", id);
        Reservacion reservacion = getReservacionOrThrow(id);
        
        if(reservacion.getEstadoReserva().equals(EstadoReserva.EN_CURSO)) {
        	throw new IllegalArgumentException("No se puede eliminar una reserva en curso");
        }
        
        servicesClients.cambioEstadoHabitacion(reservacion.getIdHabitacion(), EstadoHabitacion.DISPONIBLE);
        
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
	
	@Override
	public boolean habitacionesTieneReservacionesActivas(Long idHabitacion) {
		return reservacionRepository.existsByIdHabitacionAndEstadoRegistro(idHabitacion, EstadoRegistro.ACTIVO);
	}
	
	@Override
	public void eliminarReservacionSiHuespedEliminado(Long idHuesped) {
	
		Reservacion reservacion = reservacionRepository.findByIdHuespedAndEstadoRegistro(idHuesped, EstadoRegistro.ACTIVO)
				.orElse(null);
		
		if(reservacion == null) return;
		
		reservacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
		servicesClients.cambioEstadoHabitacion(reservacion.getIdHabitacion(), EstadoHabitacion.DISPONIBLE);
	      
	}
	
	/*-------------------Comienzan metodos privados-----------*/
	
	private Reservacion getReservacionOrThrow(Long id) {
		log.info("Buscando reservacion activa por id: {}",id);
		return reservacionRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(
				()-> new NoSuchElementException("No se econtro reservacion activa con id: " + id));
	}
	
	private HabitacionResponse getHabitacionResponse(Long id) {
		return servicesClients.obtenerHabitacionPorId(id);
	}
	
	private HuespedResponse getHuespedResponse(Long id) {
		return servicesClients.obtenerHuespedPorId(id);
	}
	
	private HabitacionResponse getHabitacionResponseSinEstado(Long id) {
		return servicesClients.obtenerHabitacionPorIdSinEstado(id);
	}
	
	private HuespedResponse getHuespedResponseSinEstado(Long id) {
		return servicesClients.obtenerHuespedPorIdSinEstado(id);
	}

//	private void verificarSiHorarioCruzado(ReservacionRequest request) {
//		if(reservacionRepository.chocaHorario(request.fechaInicio(),
//				request.fechaFin(), request.idHabitacion(), EstadoRegistro.ACTIVO.toString(), EstadoReserva.CONFIRMADA.toString() ) > 0) {
//			throw new IllegalArgumentException("La habitacion con id: " + request.idHabitacion() +
//					"se encuentra ocupada");
//		}
//	}

}
