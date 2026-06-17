package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.filter.ApartamentoFilter;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;

import java.util.Optional;

public interface ApartamentoPort {
    ApartamentoModel guardar(ApartamentoModel apartamento);
    Optional<ApartamentoModel> obtenerPorId(Long id);
    Pagina<ApartamentoModel> obtenerTodos(Paginable paginable, ApartamentoFilter filtro);
    int contarPorCondominio(Long idCondominio);
    void eliminarPorId(Long id);
}

