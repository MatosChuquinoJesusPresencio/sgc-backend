package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.filter.CondominioFilter;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;

import java.util.Optional;

public interface CondominioPort {
    CondominioModel guardar(CondominioModel condominio);
    Optional<CondominioModel> obtenerPorId(Long id);
    Pagina<CondominioModel> obtenerTodos(Paginable paginable, CondominioFilter filtro);
    void eliminarPorId(Long id);
}

