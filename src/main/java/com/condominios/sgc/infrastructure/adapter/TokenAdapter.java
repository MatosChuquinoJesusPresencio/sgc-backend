package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.model.UsuarioModel;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.domain.port.UsuarioPort;
import com.condominios.sgc.infrastructure.persistence.entity.TokenEntity;
import com.condominios.sgc.infrastructure.persistence.mapper.TokenMapper;
import com.condominios.sgc.infrastructure.persistence.repository.TokenRepository;
import com.condominios.sgc.infrastructure.util.JwtTokenFactory;
import com.condominios.sgc.infrastructure.util.TokenFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class TokenAdapter implements TokenPort {

    private final TokenRepository repository;
    private final TokenMapper mapper;
    private final JwtTokenFactory jwtTokenFactory;
    private final TokenFactory tokenFactory;
    private final UsuarioPort usuarioPort;

    public TokenAdapter(TokenRepository repository, TokenMapper mapper,
                        JwtTokenFactory jwtTokenFactory, TokenFactory tokenFactory,
                        UsuarioPort usuarioPort) {
        this.repository = repository;
        this.mapper = mapper;
        this.jwtTokenFactory = jwtTokenFactory;
        this.tokenFactory = tokenFactory;
        this.usuarioPort = usuarioPort;
    }

    @Override
    public TokenModel guardar(TokenModel token) {
        TokenEntity entidad = mapper.aEntidad(token);
        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public Optional<TokenModel> obtenerPorId(Long id) {
        return repository.findById(id).map(mapper::aModelo);
    }

    @Override
    public Optional<TokenModel> obtenerPorToken(String token) {
        return repository.findByToken(token).map(mapper::aModelo);
    }

    @Override
    public List<TokenModel> obtenerTodos() {
        return repository.findAll().stream().map(mapper::aModelo).toList();
    }

    @Override
    public List<TokenModel> obtenerPorUsuario(Long idUsuario) {
        return repository.findByIdUsuario(idUsuario).stream().map(mapper::aModelo).toList();
    }

    @Override
    public List<TokenModel> obtenerNoUsados() {
        return repository.findByUsadoFalse().stream().map(mapper::aModelo).toList();
    }

    @Override
    public TokenModel generarToken(TipoToken tipo, Long idUsuario, boolean recuerdame) {
        UsuarioModel usuario = usuarioPort.obtenerPorId(idUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + idUsuario));

        TokenModel token = jwtTokenFactory.crearToken(
                tipo, idUsuario, usuario.getCorreo(), usuario.getRol().name(),
                usuario.getNombres(), usuario.getApellidos(),
                usuario.getIdCondominio(), recuerdame);

        return guardar(token);
    }

    @Override
    public TokenModel generarToken(TipoToken tipo, Long idUsuario) {
        TokenModel token = tokenFactory.crearToken(tipo, idUsuario);
        return guardar(token);
    }

    @Override
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void eliminarPorToken(String token) {
        repository.findByToken(token).ifPresent(t -> repository.deleteById(t.getId()));
    }
}
