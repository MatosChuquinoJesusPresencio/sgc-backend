package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.VerificacionTokenModel;
import com.condominios.sgc.domain.port.VerificacionTokenPort;
import com.condominios.sgc.infrastructure.persistence.mapper.VerificacionTokenMapper;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VerificacionTokenRepository;

import java.util.Optional;

public class VerificacionTokenAdapter implements VerificacionTokenPort {

    private final VerificacionTokenRepository repository;
    private final UsuarioRepository usuarioRepository;

    public VerificacionTokenAdapter(VerificacionTokenRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<VerificacionTokenModel> findByTokenAndUsadoFalse(String token) {
        return repository.findByTokenAndUsadoFalse(token)
            .map(VerificacionTokenMapper::toModel);
    }

    @Override
    public VerificacionTokenModel save(VerificacionTokenModel model) {
        var usuario = usuarioRepository.getReferenceById(model.getUsuarioId());
        var entity = VerificacionTokenMapper.toEntity(model, usuario);
        var saved = repository.save(entity);
        return VerificacionTokenMapper.toModel(saved);
    }
}
