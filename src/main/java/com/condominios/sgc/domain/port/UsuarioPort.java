package com.condominios.sgc.domain.port;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.dto.request.PaginacionRequest;
import com.condominios.sgc.domain.dto.response.PaginacionResponse;
import com.condominios.sgc.domain.filter.UsuarioFilter;
import com.condominios.sgc.domain.model.UsuarioModel;
import java.util.List;
import java.util.Optional;

public interface UsuarioPort {
    UsuarioModel guardar(UsuarioModel usuario);
    Optional<UsuarioModel> obtenerPorId(Long id);
    Optional<UsuarioModel> obtenerPorCorreo(String correo);
    List<UsuarioModel> obtenerTodos();
    PaginacionResponse<UsuarioModel> obtenerTodos(PaginacionRequest request, UsuarioFilter filtro);
    List<UsuarioModel> obtenerPorRol(Rol rol);
    List<UsuarioModel> obtenerPorCondominio(Long idCondominio);
    void eliminarPorId(Long id);
}

