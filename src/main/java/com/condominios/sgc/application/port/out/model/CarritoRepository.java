package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.CarritoModel;

import java.util.List;
import java.util.Optional;

public interface CarritoRepository {
    Optional<CarritoModel> buscarPorId(Long id);
    Optional<CarritoModel> buscarPorCodigo(String codigo);
    List<CarritoModel> listarPorCondominio(Long idCondominio);
    CarritoModel guardar(CarritoModel carrito);
    void eliminar(Long id);
}
