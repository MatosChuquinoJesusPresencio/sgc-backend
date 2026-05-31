package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.RestablecimientoTokenModel;
import com.condominios.sgc.domain.exception.UsuarioException;
import com.condominios.sgc.domain.port.RestablecimientoTokenPort;
import com.condominios.sgc.infrastructure.persistence.mapper.RestablecimientoTokenMapper;
import com.condominios.sgc.infrastructure.persistence.repository.RestablecimientoTokenRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RestablecimientoTokenAdapter implements RestablecimientoTokenPort {

    private final RestablecimientoTokenRepository restablecimientoTokenRepository;
    private final UsuarioRepository usuarioRepository;

    public RestablecimientoTokenAdapter(RestablecimientoTokenRepository restablecimientoTokenRepository,
                                        UsuarioRepository usuarioRepository) {
        this.restablecimientoTokenRepository = restablecimientoTokenRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<RestablecimientoTokenModel> findByTokenAndUsadoFalse(String token) {
        return restablecimientoTokenRepository.findByTokenAndUsadoFalse(token).map(RestablecimientoTokenMapper::toModel);
    }

    @Override
    public RestablecimientoTokenModel save(RestablecimientoTokenModel model) {
        var usuario = usuarioRepository.findById(model.getUsuarioId())
            .orElseThrow(() -> UsuarioException.noEncontrado());
        var entity = RestablecimientoTokenMapper.toEntity(model, usuario);
        var saved = restablecimientoTokenRepository.save(entity);
        return RestablecimientoTokenMapper.toModel(saved);
    }
}
