package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.DashboardPort;
import com.condominios.sgc.infrastructure.persistence.entity.CondominioEntity;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.CondominioMapper;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.CondominioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DashboardAdapter implements DashboardPort {

    private final UsuarioRepository usuarioRepository;
    private final CondominioRepository condominioRepository;
    private final UsuarioMapper usuarioMapper;
    private final CondominioMapper condominioMapper;

    public DashboardAdapter(UsuarioRepository usuarioRepository,
                            CondominioRepository condominioRepository,
                            UsuarioMapper usuarioMapper,
                            CondominioMapper condominioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
        this.usuarioMapper = usuarioMapper;
        this.condominioMapper = condominioMapper;
    }

    @Override
    public int contarUsuariosPorRol(Rol rol) {
        return usuarioRepository.findByRol(rol).size();
    }

    @Override
    public int contarTodosLosUsuario() {
        return (int) usuarioRepository.count();
    }

    @Override
    public int contarUsuariosPorActivo(Boolean activo) {
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getActivo().equals(activo))
                .toList().size();
    }

    @Override
    public List<UsuarioModel> obtenerCincoUsuariosRecientes() {
        return usuarioRepository.findAll(Sort.by(Sort.Direction.DESC, UsuarioEntity::getFechaCreacion))
                .stream().limit(5).map(usuarioMapper::aModelo).toList();
    }

    @Override
    public List<CondominioModel> obtenerCincoCondominiosRecientes() {
        return condominioRepository.findAll(Sort.by(Sort.Direction.DESC, CondominioEntity::getFechaCreacion))
                .stream().limit(5).map(condominioMapper::aModelo).toList();
    }
}
