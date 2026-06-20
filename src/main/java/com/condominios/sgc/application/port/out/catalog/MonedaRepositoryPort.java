package com.condominios.sgc.application.port.out.catalog;

import com.condominios.sgc.domain.model.catalog.Moneda;

import java.util.List;
import java.util.Optional;

public interface MonedaRepositoryPort {
    Optional<Moneda> buscarPorId(Long id);
    List<Moneda> listarTodos();
}
