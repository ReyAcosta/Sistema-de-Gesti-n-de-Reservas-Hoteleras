package com.vdr.common_reservaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CommonReservacionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonReservacionesApplication.class, args);
	}

}
