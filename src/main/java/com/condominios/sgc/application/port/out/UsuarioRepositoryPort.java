package com.condominios.sgc.application.port.out;

import com.condominios.sgc.domain.model.UsuarioModel;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepositoryPort {
    UsuarioModel guardar(UsuarioModel usuario);
    Optional<UsuarioModel> buscarPorId(Long id);
    Optional<UsuarioModel> buscarPorCorreo(String correo);
    boolean existePorCorreo(String correo);
    List<UsuarioModel> listarTodos();
    void eliminar(Long id);
}
