package com.condominios.sgc.infrastructure.adapter;

import com.condominios.sgc.domain.auxiliar.TipoToken;
import com.condominios.sgc.domain.model.TokenModel;
import com.condominios.sgc.domain.exception.TokenException;
import com.condominios.sgc.domain.port.TokenPort;
import com.condominios.sgc.infrastructure.persistence.mapper.TokenMapper;
import com.condominios.sgc.infrastructure.persistence.repository.TokenRepository;
import com.condominios.sgc.infrastructure.persistence.repository.UsuarioRepository;
import com.condominios.sgc.infrastructure.util.JwtTokenFactory;
import com.condominios.sgc.infrastructure.util.TokenFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Component
public class TokenAdapter implements TokenPort {

    private final TokenRepository repository;
    private final TokenMapper mapper;
    private final TokenFactory tokenFactory;
    private final JwtTokenFactory jwtTokenFactory;
    private final UsuarioRepository usuarioRepository;

    public TokenAdapter(TokenRepository repository, TokenMapper mapper,
                        TokenFactory tokenFactory, JwtTokenFactory jwtTokenFactory,
                        UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
        this.jwtTokenFactory = jwtTokenFactory;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public TokenModel guardar(TokenModel modelo) {
        var entidad = mapper.aEntidad(modelo);
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
    @Transactional
    public TokenModel generarToken(TipoToken tipo, Long idUsuario, boolean recuerdame) {
        TokenModel modelo = switch (tipo) {
            case ACCESS, REFRESH -> {
                var usuarioEntity = usuarioRepository.findById(idUsuario)
                        .orElseThrow(() -> TokenException.noEncontrado());
                yield jwtTokenFactory.crearToken(tipo, idUsuario,
                        usuarioEntity.getCorreo(), usuarioEntity.getRol().name(),
                        usuarioEntity.getNombres(), usuarioEntity.getApellidos(), usuarioEntity.getIdCondominio(), recuerdame);
            }
            case VERIFICACION, REESTABLECIMIENTO -> tokenFactory.crearToken(tipo, idUsuario);
        };

        var entidad = repository.findByIdUsuarioAndTipo(idUsuario, tipo)
                .orElseGet(() -> mapper.aEntidad(modelo));

        entidad.setToken(modelo.getToken());
        entidad.setExpiracion(modelo.getExpiracion());
        entidad.setUsado(false);

        return mapper.aModelo(repository.save(entidad));
    }

    @Override
    public TokenModel generarToken(TipoToken tipo, Long idUsuario) {
        return generarToken(tipo, idUsuario, false);
    }

    @Override
    @Transactional
    public void eliminarPorId(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarPorToken(String token) {
        repository.findByToken(token).ifPresent(entity -> repository.delete(entity));
    }
}
