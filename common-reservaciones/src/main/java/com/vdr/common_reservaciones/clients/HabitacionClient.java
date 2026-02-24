package com.vdr.common_reservaciones.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;

@FeignClient(name= "habitaciones-msv")
public interface HabitacionClient {
	
	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);

	@GetMapping("/{id}/validaEstado")
	public void validarEstadoHabitacion(@PathVariable Long id) ;
	
	@PatchMapping("/{id}/estado/{idEstado}")
	public ResponseEntity<HabitacionResponse> actualizarEstadoHabitacion(@PathVariable Long id,@PathVariable Long idEstado);

}
