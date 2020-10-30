package com.mx.org.concentradora.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mx.org.concentradora.model.TransaccionOut;

@FeignClient(name = "servicio-transacciones-salida")
public interface TransaccionOutFeignClient {

	@GetMapping("/transacciones-salida/search/buscar-folio")
	public CollectionModel<TransaccionOut> findByFolio(@RequestParam(value = "folio") String folio);

	@PostMapping("/transacciones-salida/")
	public TransaccionOut save(@RequestBody TransaccionOut transaccion);

	@PutMapping("/transacciones-salida/{id}")
	public TransaccionOut update(@RequestBody TransaccionOut transaccion, @PathVariable(value = "id") Long id);
}
