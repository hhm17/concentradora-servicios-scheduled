package com.mx.org.concentradora.repository;

import java.util.Optional;

import com.mx.org.concentradora.entity.Request;

public interface TaeRepository{

	public Optional<Request> findById(Long folio);
	
}
