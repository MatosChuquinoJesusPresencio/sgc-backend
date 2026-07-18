package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.command.CrearPropietarioInquilinoCommand;
import com.condominios.sgc.application.dto.command.EditarPropietarioInquilinoCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.PropietarioInquilinoResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarPropietarioInquilinosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.InquilinoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.Rol;
import com.condominios.sgc.domain.type.TipoDocumento;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarPropietarioInquilinosService implements GestionarPropietarioInquilinosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioIdResolver condominioIdResolver;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final InquilinoRepositoryPort inquilinoRepository;
    private final VehiculoRepositoryPort vehiculoRepository;

    public GestionarPropietarioInquilinosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioIdResolver condominioIdResolver,
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.condominioIdResolver = condominioIdResolver;
        this.apartamentoRepository = apartamentoRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    private Long resolverIdApartamento(Long condominioIdOverride, Long apartamentoIdOverride) {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        if (usuario.getRol() == Rol.SUPER_ADMINISTRADOR) {
            condominioIdResolver.resolver(condominioIdOverride);
            if (apartamentoIdOverride == null) {
                return null;
            }
            return apartamentoIdOverride;
        }
        var apt = apartamentoRepository.buscarPorPropietario(usuario.getId())
            .orElseThrow(ApartamentoException::noEncontrado);
        return apt.getId();
    }

    @Override
    public List<PropietarioInquilinoResult> listar(Long condominioIdOverride, Long apartamentoIdOverride) {
        var idApartamento = resolverIdApartamento(condominioIdOverride, apartamentoIdOverride);
        if (idApartamento == null) {
            var condominioId = condominioIdResolver.resolver(condominioIdOverride);
            var apartments = apartamentoRepository.buscarEnCondominio(condominioId, null,
                new PaginaQuery(0, Integer.MAX_VALUE));
            return apartments.items().stream()
                .flatMap(apt -> apt.inquilinos().stream())
                .map(inq -> new PropietarioInquilinoResult(inq.id(), inq.nombres(), inq.apellidos(),
                    inq.tipoDocumento(), inq.numeroDocumento()))
                .toList();
        }
        return inquilinoRepository.buscarPorApartamento(idApartamento)
            .stream()
            .map(this::toResult)
            .toList();
    }

    @Override
    @Transactional
    public PropietarioInquilinoResult crear(Long condominioIdOverride, CrearPropietarioInquilinoCommand cmd) {
        var idApartamento = resolverIdApartamento(condominioIdOverride, cmd.apartamentoId());
        var modelo = new InquilinoModel(
            cmd.nombres(), cmd.apellidos(),
            TipoDocumento.valueOf(cmd.tipoDocumento()), cmd.numeroDocumento(),
            idApartamento);
        return toResult(inquilinoRepository.guardar(modelo));
    }

    @Override
    @Transactional
    public PropietarioInquilinoResult editar(Long condominioIdOverride, Long id, EditarPropietarioInquilinoCommand cmd) {
        var inquilino = inquilinoRepository.buscarPorId(id)
            .orElseThrow(InquilinoException::noEncontrado);
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        if (usuario.getRol() != Rol.SUPER_ADMINISTRADOR) {
            condominioIdResolver.resolver(condominioIdOverride);
            if (!inquilino.getIdApartamento().equals(
                apartamentoRepository.buscarPorPropietario(usuario.getId())
                    .orElseThrow(ApartamentoException::noEncontrado).getId())) {
                throw InquilinoException.noEncontrado();
            }
        }
        inquilino.actualizar(cmd.nombres(), cmd.apellidos(),
            TipoDocumento.valueOf(cmd.tipoDocumento()), cmd.numeroDocumento());
        return toResult(inquilinoRepository.guardar(inquilino));
    }

    @Override
    @Transactional
    public void eliminar(Long condominioIdOverride, Long id) {
        var inquilino = inquilinoRepository.buscarPorId(id)
            .orElseThrow(InquilinoException::noEncontrado);
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        if (usuario.getRol() != Rol.SUPER_ADMINISTRADOR) {
            condominioIdResolver.resolver(condominioIdOverride);
            if (!inquilino.getIdApartamento().equals(
                apartamentoRepository.buscarPorPropietario(usuario.getId())
                    .orElseThrow(ApartamentoException::noEncontrado).getId())) {
                throw InquilinoException.noEncontrado();
            }
        }
        vehiculoRepository.eliminarPorInquilino(id);
        inquilinoRepository.eliminarPorId(id);
    }

    private PropietarioInquilinoResult toResult(InquilinoModel m) {
        return new PropietarioInquilinoResult(
            m.getId(),
            m.getNombreCompleto().nombres(),
            m.getNombreCompleto().apellidos(),
            m.getNumeroDocumento().tipo().name(),
            m.getNumeroDocumento().numero());
    }
}
