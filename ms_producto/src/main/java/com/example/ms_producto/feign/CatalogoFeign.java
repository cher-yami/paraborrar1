package com.example.ms_producto.feign;

import com.example.ms_producto.dto.CategoriaDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-catalogo-service", path = "/categorias")
public interface CatalogoFeign {
    @GetMapping("/{id}")
    @CircuitBreaker(name = "clientByIdCB", fallbackMethod = "categoriaListarPorIdCB")
    public ResponseEntity<CategoriaDto> buscarCategoria(@PathVariable Integer id);

    public ResponseEntity<CategoriaDto> getById(@PathVariable(required = true) Integer id);

    default ResponseEntity<CategoriaDto> categoriaListarPorIdCB(Integer id, Exception e) {
        CategoriaDto clientDto = new CategoriaDto();
        clientDto.setNombre("Servicio no disponible de cliente");
        return ResponseEntity.ok(clientDto);
    }
}