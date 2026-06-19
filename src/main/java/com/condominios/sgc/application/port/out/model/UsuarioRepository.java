package com.condominios.sgc.application.port.out.model;

import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.shared.value_objects.Correo;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<UsuarioModel> buscarPorId(Long id);
    Optional<UsuarioModel> buscarPorCorreo(Correo correo);
    List<UsuarioModel> listarTodos();
    UsuarioModel guardar(UsuarioModel usuario);
    void eliminar(Long id);
    boolean existePorCorreo(Correo correo);
}
