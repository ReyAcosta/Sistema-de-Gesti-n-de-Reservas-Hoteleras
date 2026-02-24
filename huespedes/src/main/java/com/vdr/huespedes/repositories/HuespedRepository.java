package com.vdr.huespedes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.enums.TipoDocumento;
import com.vdr.huespedes.entities.Huesped;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long>{
	List<Huesped> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
	
	Boolean existsByEmailIgnoreCaseAndEstadoRegistro(String email,EstadoRegistro estadoRegistro);
	
	Boolean existsByEmailIgnoreCaseAndIdNotAndEstadoRegistro( String email,Long id, EstadoRegistro estadoRegistro);
	
	Boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);
	
	Boolean existsByTelefonoAndIdNotAndEstadoRegistro(String telefono,Long id,EstadoRegistro estadoRegistro);
	
	Boolean existsByTipoDocumentoAndEstadoRegistro(TipoDocumento tipoDocumento, EstadoRegistro estadoRegistro);
	
	Boolean existsByTipoDocumentoAndIdNotAndEstadoRegistro(TipoDocumento tipoDocumento,Long id, EstadoRegistro estadoRegistro);

}
