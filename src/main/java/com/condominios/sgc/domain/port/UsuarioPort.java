package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.filter.UsuarioFilter;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.pagination.Pagina;
import com.condominios.sgc.domain.pagination.Paginable;

import java.util.List;
import java.util.Optional;

public interface UsuarioPort {
    UsuarioModel guardar(UsuarioModel usuario);
    Optional<UsuarioModel> obtenerPorId(Long id);
    Optional<UsuarioModel> obtenerPorCorreo(String correo);
    List<UsuarioModel> obtenerTodos();
    Pagina<UsuarioModel> obtenerTodos(Paginable paginable, UsuarioFilter filtro);
    List<UsuarioModel> obtenerPorRol(Rol rol);
    List<UsuarioModel> obtenerPorCondominio(Long idCondominio);
    int contarPorCondominio(Long idCondominio);
    void eliminarPorId(Long id);
}

