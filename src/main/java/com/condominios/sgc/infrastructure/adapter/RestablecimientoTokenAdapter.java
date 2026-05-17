package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.RestablecimientoTokenModel;
import com.condominios.sgc.domain.port.RestablecimientoTokenPort;
import com.condominios.sgc.infrastructure.persistence.mapper.RestablecimientoTokenMapper;
import com.condominios.sgc.infrastructure.persistence.repository.RestablecimientoTokenRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;

import java.util.Optional;

public class RestablecimientoTokenAdapter implements RestablecimientoTokenPort {

    private final RestablecimientoTokenRepository repository;
    private final UsuarioRepository usuarioRepository;

    public RestablecimientoTokenAdapter(RestablecimientoTokenRepository repository, UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<RestablecimientoTokenModel> findByTokenAndUsadoFalse(String token) {
        return repository.findByTokenAndUsadoFalse(token)
            .map(RestablecimientoTokenMapper::toModel);
    }

    @Override
    public RestablecimientoTokenModel save(RestablecimientoTokenModel model) {
        var usuario = usuarioRepository.getReferenceById(model.getUsuarioId());
        var entity = RestablecimientoTokenMapper.toEntity(model, usuario);
        var saved = repository.save(entity);
        return RestablecimientoTokenMapper.toModel(saved);
    }
}
