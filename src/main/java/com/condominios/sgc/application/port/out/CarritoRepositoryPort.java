package com.condominios.sgc.application.port.out;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.domain.model.CarritoModel;

public interface CarritoRepositoryPort {
    Optional<CarritoModel> buscarPorId(Long id);
    CarritoModel guardar(CarritoModel modelo);
    void eliminarPorId(Long id);
    long contarPorCondominio(Long idCondominio);
    List<CarritoModel> buscarPorCondominio(Long idCondominio);
}
