package com.vdr.huespedes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.huespedes.entities.Huesped;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long>{
	List<Huesped> findByEstadoRegistro(EstadoRegistro estadoRegistro);
}
