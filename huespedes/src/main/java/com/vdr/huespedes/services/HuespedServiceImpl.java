package com.vdr.huespedes.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.vdr.common_reservaciones.clients.ReservaClient;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.enums.TipoDocumento;
import com.vdr.common_reservaciones.exceptions.EntidadRelacionadaException;
import com.vdr.common_reservaciones.exceptions.ReglaDeNegocioInvalidaException;
import com.vdr.huespedes.entities.Huesped;
import com.vdr.huespedes.mappers.HuespedMapper;
import com.vdr.huespedes.repositories.HuespedRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class HuespedServiceImpl implements HuespedService {
	private final HuespedRepository huespedRepository;
	private final HuespedMapper huespedMapper;
	private final ReservaClient reservaClient;
	
	@Override
	public List<HuespedResponse> listar() {
		log.info("Listando reservaciones activas");
        return huespedRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
                .map(huespedMapper::entityToResponse).toList();		
	}
	
	@Override
	public HuespedResponse obtenerPorId(Long id) {
        return huespedMapper.entityToResponse(getHuespedOrThrow(id));
	}
	
	@Override
	public HuespedResponse obtenerPorIdSinEstado(Long id) {
        return huespedMapper.entityToResponse(getHuespedOrThrowSinEstado(id));
	}
	
	@Override
	public HuespedResponse registrar(HuespedRequest request) {
		log.info("Registrando nuevo huesped");
		validarDatosUnicos(request);
		
        Huesped huesped = huespedRepository.save(huespedMapper.requestToEntity(request));

        return huespedMapper.entityToResponse(huesped);
	}
	
	@Override
	public HuespedResponse actualizar(HuespedRequest request, Long id) {
		 log.info("Actualizando reservación con id: {}", id);
	        Huesped huesped = getHuespedOrThrow(id);
	        
	        verificarEmailUnicoActualizar(id, huesped.getEmail());
	        verificarTelefonoUnicoActualizar(id, huesped.getTelefono());
	        verificarTipoDocumentoUnicoActualizar(id, huesped.getTipoDocumento());
	        
	        huespedMapper.updateEntityFromRequest(request, huesped);

	        return huespedMapper.entityToResponse(huesped);

	}
	
	@Override
	public void eliminar(Long id) {
		log.info("Intentando eliminar huésped con id: {}", id);
		Huesped huesped = getHuespedOrThrow(id);
		
		boolean tieneReservas = reservaClient.huespedTieneReservasActivas(id);

		if (tieneReservas) {
		    throw new EntidadRelacionadaException(
		        "No se puede eliminar el huésped porque tiene reservas activas"
		    );
		}
			
		 huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);
		 
		log.info("Eliminando Huesped con id: {} ", id);
	   	
		
	}
	
	/*---------------Comienzan metodos privados-----------*/
	
	/*---------Obtener huesped activo por id------------*/
	private Huesped getHuespedOrThrow(Long id) {
		log.info("Buscando huesped activo con id: " + id);
		return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(
				() -> new NoSuchElementException("No se encotro un huesped activo con el id: "+ id));
	}
	
	/*---------Obtener huesped sin importar estado por id------------*/
	private Huesped getHuespedOrThrowSinEstado(Long id) {
		log.info("Buscando huesped activo con id: " + id);
		return huespedRepository.findById(id).orElseThrow(
				() -> new NoSuchElementException("No se encotro un huesped con el id: "+ id));
	}
	
	/*---------verificar datos unicos al registrar---------------*/
	
	private void verificarEmailUnico(String email) {
		if(huespedRepository.existsByEmailIgnoreCaseAndEstadoRegistro(email, EstadoRegistro.ACTIVO)) {
			throw new ReglaDeNegocioInvalidaException("Ya existe un huesped con email: " + email);
		}
	}
	
	private void verificarTelefonoUnico(String telefono) {
		if(huespedRepository.existsByTelefonoAndEstadoRegistro(telefono, EstadoRegistro.ACTIVO)) {
			throw new ReglaDeNegocioInvalidaException("Ya existe un huesped con telefono: " + telefono);
		}
	}
	
	private void verificarDocumentoUnico(TipoDocumento tipoDocumento) {
		if(huespedRepository.existsByTipoDocumentoAndEstadoRegistro(tipoDocumento, EstadoRegistro.ACTIVO)) {
			throw new ReglaDeNegocioInvalidaException("Ya existe un huesped con documento: " + tipoDocumento);
		}
	}
	
	private void validarDatosUnicos(HuespedRequest request) {
		verificarEmailUnico(request.email());
		verificarTelefonoUnico(request.telefono());
	//	verificarDocumentoUnico(request.idDocumento());
	}
	
	/*---------verificar datos unicos al actualizar---------------*/
	
	private void verificarEmailUnicoActualizar(Long id, String email) {
		if(huespedRepository.existsByEmailIgnoreCaseAndIdNotAndEstadoRegistro(email, id, EstadoRegistro.ACTIVO)) {
			throw new ReglaDeNegocioInvalidaException("Ya existe un huesped con email: " + email);
		}
	}
	
	private void verificarTelefonoUnicoActualizar(Long id, String telefono) {
		if(huespedRepository.existsByTelefonoAndIdNotAndEstadoRegistro(telefono, id, EstadoRegistro.ACTIVO)) {
			throw new ReglaDeNegocioInvalidaException("Ya existe un huesped con telefono: " + telefono);
		}
	}
	
	private void verificarTipoDocumentoUnicoActualizar(Long id, TipoDocumento tipoDocumento) {
		if(huespedRepository.existsByTipoDocumentoAndIdNotAndEstadoRegistro(tipoDocumento, id, EstadoRegistro.ACTIVO)) {
			throw new ReglaDeNegocioInvalidaException("Ya existe un huesped con documento: " + tipoDocumento);
		}
	}
}
