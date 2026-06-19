package com.condominios.sgc.application.port.out.catalog;

import com.condominios.sgc.domain.catalog.Moneda;

import java.util.List;
import java.util.Optional;

public interface MonedaRepository {
    Optional<Moneda> buscarPorId(long id);
    List<Moneda> listarTodos();
}
