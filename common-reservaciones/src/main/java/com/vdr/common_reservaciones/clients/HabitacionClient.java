package com.vdr.common_reservaciones.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vdr.common_reservaciones.dtos.habitaciones.HabitacionResponse;

@FeignClient(name= "habitaciones-msv")
public interface HabitacionClient {
	@GetMapping("/id-habitacion/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);

}
