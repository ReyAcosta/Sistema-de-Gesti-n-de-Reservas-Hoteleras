package com.vdr.reservaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.vdr.reservaciones", "com.vdr.common_reservaciones"})
public class ReservacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservacionesApplication.class, args);
	}

}
