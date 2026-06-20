package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.CondominioModel;

import java.util.List;
import java.util.Optional;

public interface CondominioRepositoryPort {
    CondominioModel guardar(CondominioModel condominio);
    Optional<CondominioModel> buscarPorId(Long id);
    List<CondominioModel> listarTodos();
    void eliminar(Long id);
    boolean existsById(Long id);
}
