package com.vdr.reservaciones.entities;

import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.reservaciones.enums.EstadoReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name="RESERVACIONES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Reservacion{
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Long id;

	    @Column(name = "ID_HUESPED", nullable = false)
	    private Long idHuesped;
	
        @Column(name = "ID_HABITACION", nullable = false)
        private Long idHabitacion;

	    @Column(name = "FECHA_RESERVA", updatable = false)
	    @CreationTimestamp
	    private LocalDateTime fechaReserva;
	    
	    @Column(name = "FECHA_INICIO") 
	    private LocalDateTime fechaInicio;
	    
	    @Column(name = "FECHA_FIN")
	    private LocalDateTime fechaFin;

	
	    @Column(name = "ESTADO_RESERVA", nullable = false)
        @Enumerated(EnumType.STRING)
        private EstadoReserva estadoReserva;
	
        @Column(name = "ESTADO_REGISTRO", nullable = false)
        @Enumerated(EnumType.STRING)
        private EstadoRegistro estadoRegistro;

}

