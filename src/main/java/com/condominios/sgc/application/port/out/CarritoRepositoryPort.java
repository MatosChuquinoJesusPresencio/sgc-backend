package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.CarritoModel;

import java.util.List;
import java.util.Optional;

public interface CarritoRepositoryPort {
    CarritoModel guardar(CarritoModel carrito);
    Optional<CarritoModel> buscarPorId(Long id);
    Optional<CarritoModel> buscarPorCodigo(String codigo);
    List<CarritoModel> listarPorCondominio(Long idCondominio);
    long contarPorCondominio(Long idCondominio);
}
