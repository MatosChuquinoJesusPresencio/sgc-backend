package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.filter.TorreFilter;
import com.condominios.sgc.domain.model.TorreModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;

import java.util.List;
import java.util.Optional;

public interface TorrePort {
    TorreModel guardar(TorreModel torre);
    Optional<TorreModel> obtenerPorId(Long id);
    List<TorreModel> obtenerTodos();
    Pagina<TorreModel> obtenerTodos(Paginable request, TorreFilter filtro);
    List<TorreModel> obtenerPorCondominio(Long idCondominio);
    void eliminarPorId(Long id);
}

