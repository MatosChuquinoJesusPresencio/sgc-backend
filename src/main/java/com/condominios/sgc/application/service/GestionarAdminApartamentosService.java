package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.ActualizarOcupantesCommand;
import com.condominios.sgc.application.dto.command.AsignarPropietarioCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.port.in.GestionarAdminApartamentosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdminApartamentosService implements GestionarAdminApartamentosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final InquilinoRepositoryPort inquilinoRepository;
    private final VehiculoRepositoryPort vehiculoRepository;


    public GestionarAdminApartamentosService(
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

    @Override
    public PaginaResult<AdminApartamentoDetailResult> listar(PaginaQuery pagina) {
        var condominioId = obtenerCondominioId();
        return apartamentoRepository.buscarEnCondominio(condominioId, pagina);
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public void actualizarOcupantes(Long apartamentoId, ActualizarOcupantesCommand cmd) {
        if (apartamentoRepository.buscarPorId(apartamentoId).isEmpty()) {
            throw ApartamentoException.noEncontrado();
        }
        for (var i : inquilinoRepository.buscarPorApartamento(apartamentoId)) {
            vehiculoRepository.eliminarPorInquilino(i.getId());
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
