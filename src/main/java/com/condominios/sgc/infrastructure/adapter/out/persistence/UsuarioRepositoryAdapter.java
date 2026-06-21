package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.UsuarioMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.UsuarioJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository repository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UsuarioModel> buscarPorId(Long id) {
        return repository.findById(id).map(UsuarioMapper::toModel);
    }

    @Override
    public Optional<UsuarioModel> buscarPorCorreo(String correo) {
        return repository.findByCorreo(correo).map(UsuarioMapper::toModel);
    }

    @Override
    public UsuarioModel guardar(UsuarioModel modelo) {
        return UsuarioMapper.toModel(repository.save(UsuarioMapper.toEntity(modelo)));
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }
}
