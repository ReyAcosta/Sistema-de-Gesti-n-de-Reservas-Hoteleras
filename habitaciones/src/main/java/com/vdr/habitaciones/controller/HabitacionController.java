package com.vdr.habitaciones.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdr.common_reservaciones.controllers.CommonController;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionRequest;
import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.habitaciones.services.HabitacionService;


@RestController
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService>{

	public HabitacionController(HabitacionService service) {
		super(service);
		// TODO Auto-generated constructor stub
	}
	
	@GetMapping("/id-habitacion/{id}")
	public ResponseEntity<HabitacionResponse> obtenerPorIdSinEstaod(@PathVariable Long id){
		return ResponseEntity.ok(service.obtenerPorIdSinEstado(id));
	}
	
	@PatchMapping("/{id}/estado/{idEstado}")
	public ResponseEntity<HabitacionResponse> actualizarEstadoHabitacion(@PathVariable Long id,
																		@PathVariable Long idEstado){
		return ResponseEntity.ok(service.actualizarEstadoHabitacion(id, idEstado, false));
	}
	
	@PutMapping("/{id}/estado/{idEstado}")
	public ResponseEntity<HabitacionResponse> actualizarEstadoHabitacionSinRestriccion(@PathVariable Long id,
																		@PathVariable Long idEstado){
		return ResponseEntity.ok(service.actualizarEstadoHabitacion(id, idEstado, true));
	}
	
	@GetMapping("/{id}/validaEstado")
	public ResponseEntity<Void> validarEstadoHabitacion(@PathVariable Long id) {
		service.validarHabitacionDisponible(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{idHabitacionActual}/cambio/{idHabitacionNueva}")
	public ResponseEntity<Void> cambioHabitacion(@PathVariable Long idHabitacionActual,
												@PathVariable Long idHabitacionNueva){
		service.cambioHabitacion(idHabitacionActual, idHabitacionNueva);
		return ResponseEntity.ok().build();
	}
	
	
	
}
