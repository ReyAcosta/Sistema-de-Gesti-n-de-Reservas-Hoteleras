package com.vdr.reservaciones.controllers;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.vdr.common_reservaciones.controllers.CommonController;
import com.vdr.reservaciones.dtos.ReservacionRequest;
import com.vdr.reservaciones.dtos.ReservacionResponse;
import com.vdr.reservaciones.services.ReservacionService;

@RestController
public class ReservacionController extends CommonController<ReservacionRequest, ReservacionResponse, ReservacionService> {
	public ReservacionController(ReservacionService service) {
		super(service);
	}
	
	@GetMapping("/eliminadas")
	public ResponseEntity<List<ReservacionResponse>> listarEliminadas(){
		return ResponseEntity.ok(service.listarEliminadas());
	}

	@GetMapping("/id-reservacion/{id}")
	public ResponseEntity<ReservacionResponse> obtenerEliminada(@PathVariable Long id){
		return ResponseEntity.ok(service.obtenerPorIdSinEstado(id));
	}
	
	@PatchMapping("/{idReservacion}/estado/{idEstado}")
	public ResponseEntity<ReservacionResponse> actualizarEstadoReservacion(@PathVariable Long idReservacion,
																			@PathVariable Long idEstado){
		return ResponseEntity.ok(service.actualizarEstadoReserva(idReservacion, idEstado));
	}
	
	@GetMapping("/id-huesped/{idHuesped}/reservas-activas")
	public ResponseEntity<Void> huespedTieneConsultasConfirmadasEnCurso(@PathVariable Long idHuesped){
		service.huespedTieneConsultasConfirmadasEnCurso(idHuesped);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/id-habitacion/{idhabitacion}/reservas-activas")
	public ResponseEntity<Boolean> habitacionTieneReservacionesActivas(@PathVariable Long idhabitacion){
		
		return ResponseEntity.ok(service.habitacionesTieneReservacionesActivas(idhabitacion));
	}
	
	@GetMapping("reservacion/id-huesped/{idHuesped}")
	public ResponseEntity<Void> eliminarReservacionSiHuespedEliminado(@PathVariable Long idHuesped){
		service.eliminarReservacionSiHuespedEliminado(idHuesped);
		return ResponseEntity.ok().build();
	}
}
