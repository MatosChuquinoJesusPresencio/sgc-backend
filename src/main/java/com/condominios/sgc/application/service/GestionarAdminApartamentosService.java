package com.condominios.sgc.application.service;

import java.util.ArrayList;
import java.util.List;

import com.condominios.sgc.application.dto.command.ActualizarOcupantesCommand;
import com.condominios.sgc.application.dto.command.AsignarPropietarioCommand;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.AdminInquilinoResult;
import com.condominios.sgc.application.port.in.GestionarAdminApartamentosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.ApartamentoModel;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import jakarta.transaction.Transactional;

public class GestionarAdminApartamentosService implements GestionarAdminApartamentosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioRepositoryPort condominioRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final InquilinoRepositoryPort inquilinoRepository;

    public GestionarAdminApartamentosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.inquilinoRepository = inquilinoRepository;
    }

    @Override
    public List<AdminApartamentoDetailResult> listar() {
        var condominio = cargarCondominio();
        var results = new ArrayList<AdminApartamentoDetailResult>();
        for (var torre : condominio.getTorres()) {
            for (var piso : torre.getPisos()) {
                for (var apto : piso.getApartamentos()) {
                    results.add(toDetail(apto, torre.getNombre(), piso.getNumero()));
                }
            }
        }
        return results;
    }

    @Override
    public void asignarPropietario(Long apartamentoId, AsignarPropietarioCommand cmd) {
        var condominioId = obtenerCondominioId();
        var apto = apartamentoRepository.buscarPorId(apartamentoId)
            .orElseThrow(ApartamentoException::noEncontrado);
        if (apto.getIdPropietario() != null)
            throw ApartamentoException.yaTienePropietarioAsignado();
        var propietario = usuarioRepository.buscarPorId(cmd.idPropietario())
            .orElseThrow(UsuarioException::noEncontrado);
        if (!condominioId.equals(propietario.getIdCondominio())) {
            throw UsuarioException.noEncontrado();
        }
        apto.asignarPropietario(cmd.idPropietario());
        apartamentoRepository.guardar(apto);
    }

    @Transactional
    @Override
    public void actualizarOcupantes(Long apartamentoId, ActualizarOcupantesCommand cmd) {
        if (apartamentoRepository.buscarPorId(apartamentoId).isEmpty()) {
            throw ApartamentoException.noEncontrado();
        }
        inquilinoRepository.eliminarPorApartamento(apartamentoId);
        for (var entry : cmd.inquilinos()) {
            var inquilino = new InquilinoModel(
                entry.nombres(), entry.apellidos(),
                entry.tipoDocumento(), entry.numeroDocumento(),
                apartamentoId);
            inquilinoRepository.guardar(inquilino);
        }
    }

    private AdminApartamentoDetailResult toDetail(ApartamentoModel apto, String torreNombre, Integer pisoNumero) {
        String nombrePropietario = null;
        if (apto.getIdPropietario() != null) {
            nombrePropietario = usuarioRepository.buscarPorId(apto.getIdPropietario())
                .map(u -> u.getNombres() + " " + u.getApellidos())
                .orElse(null);
        }
        var inquilinos = inquilinoRepository.buscarPorApartamento(apto.getId()).stream()
            .map(i -> new AdminInquilinoResult(
                i.getId(), i.getNombres(), i.getApellidos(),
                i.getNumeroDocumento().tipo().name(),
                i.getNumeroDocumento().numero()))
            .toList();
        return new AdminApartamentoDetailResult(
            apto.getId(), apto.getNumero(), apto.getMetraje(),
            apto.getDerechoEstacionamiento(), apto.getIdPropietario(),
            nombrePropietario, torreNombre, pisoNumero, inquilinos);
    }

    private CondominioModel cargarCondominio() {
        return condominioRepository.buscarPorId(obtenerCondominioId())
            .orElseThrow(CondominioException::noEncontrado);
    }

    private Long obtenerCondominioId() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        return condominioId;
    }
}
