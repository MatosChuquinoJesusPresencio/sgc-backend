package com.condominios.sgc.application.port.out;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.domain.model.PaisModel;

public interface PaisRepositoryPort {
    Optional<PaisModel> buscarPorId(Long id);
    List<PaisModel> listarTodos();
}
