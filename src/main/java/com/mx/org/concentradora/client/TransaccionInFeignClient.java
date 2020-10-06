package com.mx.org.concentradora.client;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mx.org.concentradora.model.TransaccionIn;

@FeignClient(name = "servicio-transacciones-entrada")
public interface TransaccionInFeignClient {

	@GetMapping("/transacciones-entrada/search/buscar-folio")
	public TransaccionIn findByFolio(@RequestParam String folio);
	
	@GetMapping("/transacciones-entrada/{id}")
	public Optional<TransaccionIn> findById(@PathVariable Long id);

	@PostMapping("/transacciones-entrada/")
	public TransaccionIn save(@RequestBody TransaccionIn transaccion);
}
