package com.mx.org.concentradora.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mx.org.concentradora.model.Bitacora;

@FeignClient(name = "servicio-bitacoras")
public interface BitacoraFeignClient {

//	@GetMapping("/bitacoras/{id}")
//	public Bitacora findById(@PathVariable Long id);

	@GetMapping("/bitacoras/search/buscar-folio")
	public Bitacora findByFolio(@RequestParam String folio);

	@PostMapping("/bitacoras/")
	public Bitacora save(@RequestBody Bitacora bitacora);
	
	@PutMapping("/bitacoras/{id}")
	public Bitacora update(@RequestBody Bitacora bitacora, @PathVariable Long id);
}
