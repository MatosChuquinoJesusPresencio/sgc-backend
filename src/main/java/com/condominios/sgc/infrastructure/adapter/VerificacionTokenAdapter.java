package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.model.VerificacionTokenModel;
import com.condominios.sgc.domain.port.VerificacionTokenPort;
import com.condominios.sgc.infrastructure.persistence.mapper.VerificacionTokenMapper;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.persistence.repository.VerificacionTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VerificacionTokenAdapter implements VerificacionTokenPort {

    private final VerificacionTokenRepository verificacionTokenRepository;
    private final UsuarioRepository usuarioRepository;

    public VerificacionTokenAdapter(VerificacionTokenRepository verificacionTokenRepository,
                                    UsuarioRepository usuarioRepository) {
        this.verificacionTokenRepository = verificacionTokenRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Optional<VerificacionTokenModel> findByTokenAndUsadoFalse(String token) {
        return verificacionTokenRepository.findByTokenAndUsadoFalse(token).map(VerificacionTokenMapper::toModel);
    }

    @Override
    public VerificacionTokenModel save(VerificacionTokenModel model) {
        var usuario = usuarioRepository.findById(model.getUsuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + model.getUsuarioId()));
        var entity = VerificacionTokenMapper.toEntity(model, usuario);
        var saved = verificacionTokenRepository.save(entity);
        return VerificacionTokenMapper.toModel(saved);
    }
}
