package com.vdr.huespedes.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.vdr.common_reservaciones.dtos.huespedes.HuespedRequest;
import com.vdr.common_reservaciones.dtos.huespedes.HuespedResponse;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class HuespedServiceImpl implements HuespedService {
	
	@Override
	public List<HuespedResponse> listar() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HuespedResponse obtenerPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HuespedResponse registrar(HuespedRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public HuespedResponse actualizar(HuespedRequest request, Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void eliminar(Long id) {
		// TODO Auto-generated method stub
		
	}
}
