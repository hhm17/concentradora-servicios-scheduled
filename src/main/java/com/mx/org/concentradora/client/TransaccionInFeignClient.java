package com.mx.org.concentradora.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mx.org.concentradora.model.TransaccionIn;

@FeignClient(name = "servicio-transacciones-entrada")
public interface TransaccionInFeignClient {

	@GetMapping("/transacciones-entrada/search/buscar-folio")
	public TransaccionIn findByFolio(@RequestParam("folio") String folio);

	@GetMapping("/transacciones-entrada/search/buscar-estatus")
	public CollectionModel<TransaccionIn> findByEstatus(@RequestParam("estatus") Integer estatus);

	@PostMapping("/transacciones-entrada/")
	public TransaccionIn save(@RequestBody TransaccionIn transaccion);
}
