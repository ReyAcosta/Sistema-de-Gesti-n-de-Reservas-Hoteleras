package com.vdr.habitaciones;

import org.springframework.boot.SpringApplication;


import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.vdr.habitaciones", "com.vdr.common_reservaciones"})
public class HabitacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitacionesApplication.class, args);
	}

}
