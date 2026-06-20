package com.condominios.sgc.application.port.out;

import java.util.Optional;

import com.condominios.sgc.domain.model.UsuarioModel;

public interface UsuarioRepositoryPort {
    Optional<UsuarioModel> buscarPorId(Long id);
    Optional<UsuarioModel> buscarPorCorreo(String correo);
    UsuarioModel guardar(UsuarioModel modelo);
    void eliminarPorId(Long id);
}

