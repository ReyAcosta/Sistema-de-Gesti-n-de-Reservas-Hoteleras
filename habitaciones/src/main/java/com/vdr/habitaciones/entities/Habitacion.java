package com.vdr.habitaciones.entities;

import java.math.BigDecimal;

import com.vdr.common_reservaciones.enums.EstadoHabitacion;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.enums.TipoHabitacion;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name="HABITACIONES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Habitacion{
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "ID")
	    private Long id;

	    @Column(name = "NUMERO_HABITACION", nullable = false)
	    @Min(value = 1)
	    private Short numeroHabitacion;
		
	    @Enumerated(EnumType.STRING)
	    @Column(name = "TIPO_HABITACION", nullable = false)
	    private TipoHabitacion tipoHabitacion;

	    @Positive
	    @Column(name = "PRECIO", nullable = false)
	    private BigDecimal precio;

	    @Min(value = 1)
	    @Column(name = "CAPACIDAD", nullable = false)
	    private Short capacidad;
		
	    @Enumerated(EnumType.STRING)
	    @Column(name = "ESTADO_HABITACION", nullable = false)
	    private EstadoHabitacion estadoHabitacion;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "ESTADO_REGISTRO", nullable = false)
	    private EstadoRegistro estadoRegistro;

}