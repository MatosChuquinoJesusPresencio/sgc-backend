package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.CondominioModel;

import java.util.List;
import java.util.Optional;

public interface CondominioRepository {
    Optional<CondominioModel> buscarPorId(Long id);
    List<CondominioModel> listarTodos();
    CondominioModel guardar(CondominioModel condominio);
    void eliminar(Long id);
}
