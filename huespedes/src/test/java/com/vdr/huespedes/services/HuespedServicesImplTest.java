package com.vdr.huespedes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;
import com.vdr.common_reservaciones.enums.EstadoRegistro;
import com.vdr.common_reservaciones.enums.Nacionalidad;
import com.vdr.common_reservaciones.enums.TipoDocumento;
import com.vdr.huespedes.entities.Huesped;
import com.vdr.huespedes.mappers.HuespedMapper;
import com.vdr.huespedes.repositories.HuespedRepository;

@ExtendWith(MockitoExtension.class)
class HuespedServicesImplTest {
	
	   @Mock
	    private HuespedRepository repository;

	    @Mock
	    private HuespedMapper huespedMapper;

	    @InjectMocks
	    private HuespedServiceImpl serviceImpl;

	    @Test
	    void debeRetornarHuespedCuandoExiste() {
	    	
	    	
	    	

	        Huesped huesped = new Huesped(
	            1L, "Derek", "andres", "flores",
	            "derekflores@gmail.com", "7333397321",
	            null, null, null
	        );

	        HuespedResponse response =
	            new HuespedResponse(1L, "Derek", "andres", "flores",
	                                "derekflores@gmail.com", "7333397321", null, null);

	        when(repository.findByIdAndEstadoRegistro(1L, EstadoRegistro.ACTIVO))
	            .thenReturn(Optional.of(huesped));

	        when(huespedMapper.entityToResponse(huesped))
	            .thenReturn(response);

	        HuespedResponse resultado = serviceImpl.obtenerPorId(1L);

	        assertEquals("Derek", resultado.nombre());

	        verify(repository).findByIdAndEstadoRegistro(1L,  EstadoRegistro.ACTIVO);
	        verify(huespedMapper).entityToResponse(huesped);
	    }
	    
	    @Test
	    void debeRetornarHuespedResponseCuandoRegistre() {

	        HuespedRequest request = new HuespedRequest(
	            "Derek", "andres", "flores",
	            "derekflores@gmail.com", "7333397321", 1L, 1L
	        );

	        Huesped huesped = new Huesped(
	            1L, "Derek", "andres", "flores",
	            "derekflores@gmail.com", "7333397321",
	            TipoDocumento.INE, Nacionalidad.MEXICO, EstadoRegistro.ACTIVO
	        );

	        HuespedResponse response = new HuespedResponse(
	            1L, "Derek", "andres", "flores",
	            "derekflores@gmail.com", "7333397321",
	            "Credencial Electoral", "Mexicana"
	        );

	        // 🔥 IMPORTANTE: mockear validaciones
	        when(repository.existsByEmailIgnoreCaseAndEstadoRegistro(
	                request.email(), EstadoRegistro.ACTIVO))
	            .thenReturn(false);

	        when(repository.existsByTelefonoAndEstadoRegistro(
	                request.telefono(), EstadoRegistro.ACTIVO))
	            .thenReturn(false);

	        // 🔥 mockear mapper
	        when(huespedMapper.requestToEntity(request))
	            .thenReturn(huesped);

	        when(repository.save(any(Huesped.class)))
	            .thenReturn(huesped);

	        when(huespedMapper.entityToResponse(huesped))
	            .thenReturn(response);

	        HuespedResponse resultado = serviceImpl.registrar(request);

	        assertEquals("Derek", resultado.nombre());

	        verify(repository).save(any(Huesped.class));
	    }

}
