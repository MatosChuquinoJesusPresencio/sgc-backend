package com.condominios.sgc.domain.port;

import java.util.Optional;

import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.CarritoModel;

public interface CarritoPort {
    Optional<CarritoModel> findById(Long id);
    PaginacionResponse<CarritoModel> findByCondominioId(Long condominioId, PaginacionRequest request);
    CarritoModel save(CarritoModel carrito);
    void deleteById(Long id);
}
