package com.condominios.sgc.infrastructure.persistence.adapter;

import com.condominios.sgc.application.port.out.TokenRepositoryPort;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.infrastructure.persistence.entity.TokenEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.TokenMapper;
import com.condominios.sgc.infrastructure.persistence.repository.TokenJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TokenRepositoryAdapter implements TokenRepositoryPort {

    private final TokenJpaRepository jpaRepository;
    private final TokenMapper mapper;

    public TokenRepositoryAdapter(TokenJpaRepository jpaRepository, TokenMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TokenModel guardar(TokenModel token) {
        TokenEntity entity = mapper.aEntidad(token);
        return mapper.aDominio(jpaRepository.save(entity));
    }

    @Override
    public Optional<TokenModel> buscarPorId(Long id) {
        return jpaRepository.findById(id).map(mapper::aDominio);
    }

    @Override
    public Optional<TokenModel> buscarPorToken(String token) {
        return jpaRepository.findByToken(token).map(mapper::aDominio);
    }

    @Override
    public List<TokenModel> listarPorUsuario(Long idUsuario) {
        return jpaRepository.findByIdUsuario(idUsuario).stream().map(mapper::aDominio).toList();
    }
}
