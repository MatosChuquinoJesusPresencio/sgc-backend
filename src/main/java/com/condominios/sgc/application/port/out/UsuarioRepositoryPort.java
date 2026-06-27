package com.condominios.sgc.application.port.out;

import java.util.List;
import java.util.Optional;

import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.domain.model.UsuarioModel;

public interface UsuarioRepositoryPort {
    Optional<UsuarioModel> buscarPorId(Long id);
    Optional<UsuarioModel> buscarPorCorreo(String correo);
    UsuarioModel guardar(UsuarioModel modelo);
    void eliminarPorId(Long id);

    PaginaResult<UsuarioModel> buscarAdministradores(String search, Boolean activo, PaginaQuery paginacion);
    boolean existePorCorreo(String correo);
    List<UsuarioModel> buscarAdministradoresSinCondominio();
    Optional<UsuarioModel> buscarPorCondominioId(Long idCondominio);
    long contarPorRol(String rol);
    List<UsuarioModel> buscarRecientesPorRol(String rol, int limite);
    PaginaResult<UsuarioModel> buscarTodos(String search, String rol, Boolean activo, PaginaQuery paginacion);
    long contarPorCondominioYRol(Long idCondominio, String rol);
    PaginaResult<UsuarioModel> buscarPorCondominio(Long idCondominio, String search, String rol, Boolean activo, PaginaQuery paginacion);
}

