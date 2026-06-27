package com.condominios.sgc.application.port.out;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.domain.model.CondominioModel;

public interface CondominioRepositoryPort {
    Optional<CondominioModel> buscarPorId(Long id);
    Optional<CondominioModel> buscarPorNombre(String nombre);
    CondominioModel guardar(CondominioModel modelo);
    void eliminarPorId(Long id);

    List<CondominioModel> buscarActivosSinAdministrador();
    Optional<String> buscarNombrePorId(Long id);
    PaginaResult<CondominioModel> buscarTodos(String search, Boolean activo, PaginaQuery paginacion);
    long contarTodos(String search, Boolean activo);
    List<CondominioModel> buscarRecientes(int limite);
    long contarActivos();
}
