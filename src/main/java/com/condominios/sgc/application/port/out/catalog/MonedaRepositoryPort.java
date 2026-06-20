package com.condominios.sgc.application.port.out.catalog;

import java.util.Optional;

import com.condominios.sgc.domain.model.catalog.Moneda;

public interface MonedaRepositoryPort {
    Optional<Moneda> buscarPorId(Long id);
    Moneda guardar(Moneda moneda);
    void eliminarPorId(Long id);
}
