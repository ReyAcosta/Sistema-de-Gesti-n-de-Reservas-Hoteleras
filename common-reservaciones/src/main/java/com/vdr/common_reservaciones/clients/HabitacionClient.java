package com.vdr.common_reservaciones.clients;

import org.springframework.cloud.openfeign.FeignClient;


import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;
import com.vdr.common_reservaciones.enums.EstadoHabitacion;

@FeignClient(name= "habitaciones-msv")
public interface HabitacionClient {
	
	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);

	@GetMapping("/{id}/validaEstado")
	Void validarEstadoHabitacion(@PathVariable Long id) ;
	
	@PutMapping("/{id}/estado/{estado}")
	HabitacionResponse actualizarEstadoHabitacion(@PathVariable Long id,
			@PathVariable EstadoHabitacion estado);
	
	@PutMapping("/{idHabitacionActual}/cambio/{idHabitacionNueva}")
	Void cambioHabitacion(@PathVariable Long idHabitacionActual,
												@PathVariable Long idHabitacionNueva);
	
	@GetMapping("/id-habitacion/{id}")
	HabitacionResponse obtenerPorIdSinEstado(@PathVariable Long id);
}
