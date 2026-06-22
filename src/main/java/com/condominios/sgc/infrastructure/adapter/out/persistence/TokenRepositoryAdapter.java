package com.condominios.sgc.infrastructure.adapter.out.persistence;

import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.infrastructure.adapter.out.persistence.mapper.TokenMapper;
import com.condominios.sgc.infrastructure.adapter.out.persistence.repository.TokenJpaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class TokenRepositoryAdapter implements TokenRepositoryPort {

    private final TokenJpaRepository repository;

    public TokenRepositoryAdapter(TokenJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<TokenModel> obtenerPorToken(String token) {
        return repository.findByToken(token).map(TokenMapper::toModel);
    }

    @Override
    public TokenModel guardar(TokenModel modelo) {
        return TokenMapper.toModel(repository.save(TokenMapper.toEntity(modelo)));
    }

    @Override
    public void eliminar(TokenModel modelo) {
        repository.deleteById(modelo.getId());
    }

    @Override
    public void eliminarPorUsuarioId(Long usuarioId) {
        repository.deleteByIdUsuario(usuarioId);
    }
}
