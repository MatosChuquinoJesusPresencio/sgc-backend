package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.command.CrearPropietarioInquilinoCommand;
import com.condominios.sgc.application.dto.result.PropietarioInquilinoResult;
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
import com.condominios.sgc.domain.type.TipoDocumento;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarPropietarioInquilinosService implements GestionarPropietarioInquilinosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final InquilinoRepositoryPort inquilinoRepository;
    private final VehiculoRepositoryPort vehiculoRepository;

    public GestionarPropietarioInquilinosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.vehiculoRepository = vehiculoRepository;
    }

    private Long obtenerIdApartamento() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var apt = apartamentoRepository.buscarPorPropietario(usuario.getId());
        if (apt.isEmpty()) throw ApartamentoException.noEncontrado();
        return apt.get().getId();
    }

    @Override
    public List<PropietarioInquilinoResult> listar() {
        return inquilinoRepository.buscarPorApartamento(obtenerIdApartamento())
            .stream()
            .map(this::toResult)
            .toList();
    }

    @Override
    @Transactional
    public PropietarioInquilinoResult crear(CrearPropietarioInquilinoCommand cmd) {
        var idApartamento = obtenerIdApartamento();
        var modelo = new InquilinoModel(
            cmd.nombres(), cmd.apellidos(),
            TipoDocumento.valueOf(cmd.tipoDocumento()), cmd.numeroDocumento(),
            idApartamento);
        return toResult(inquilinoRepository.guardar(modelo));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        var inquilino = inquilinoRepository.buscarPorId(id)
            .orElseThrow(InquilinoException::noEncontrado);
        if (!inquilino.getIdApartamento().equals(obtenerIdApartamento())) {
            throw InquilinoException.noEncontrado();
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
