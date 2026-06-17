package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.model.PaisModel;
import java.util.List;
import java.util.Optional;

public interface PaisPort {
    Optional<PaisModel> obtenerPorId(Long id);
    List<PaisModel> obtenerTodos();
}

