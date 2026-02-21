package com.vdr.habitaciones.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.habitaciones.entities.Habitacion;

@Repository
public interface HabitacionRepository  extends JpaRepository<Habitacion, Long>{

	Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro activo); 

}
