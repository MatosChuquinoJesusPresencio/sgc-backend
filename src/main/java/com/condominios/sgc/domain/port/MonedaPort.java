package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.model.MonedaModel;
import java.util.List;
import java.util.Optional;

public interface MonedaPort {
    Optional<MonedaModel> obtenerPorId(Long id);
    Optional<MonedaModel> obtenerPorCodigo(String codigo);
    List<MonedaModel> obtenerTodos();
}

