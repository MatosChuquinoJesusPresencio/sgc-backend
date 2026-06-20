package com.condominios.sgc.infrastructure.persistence.adapter;

import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.infrastructure.persistence.entity.UsuarioEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;
    private final UsuarioMapper mapper;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository, UsuarioMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public UsuarioModel guardar(UsuarioModel usuario) {
        UsuarioEntity entity = mapper.aEntidad(usuario);
        return mapper.aDominio(jpaRepository.save(entity));
    }

    @Override
    public Optional<UsuarioModel> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::aDominio);
    }

    @Override
    public Optional<UsuarioModel> buscarPorCorreo(String correo) {
        return jpaRepository.findByCorreo(correo).map(mapper::aDominio);
    }

    @Override
    public boolean existePorCorreo(String correo) {
        return jpaRepository.existsByCorreo(correo);
    }

    @Override
    public List<UsuarioModel> listarTodos() {
        return jpaRepository.findAll().stream().map(mapper::aDominio).toList();
    }

    @Override
    public void eliminar(Long id) {
        jpaRepository.deleteById(id);
    }
}
