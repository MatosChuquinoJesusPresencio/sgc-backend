package com.condominios.sgc.domain.port;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.domain.model.CondominioModel;

public interface CondominioPort {
    Optional<CondominioModel> findById(Long id);
    List<CondominioModel> findAll();
    CondominioModel save(CondominioModel model);
    void deleteById(Long id);
    boolean existsByNombre(String nombre);
}
