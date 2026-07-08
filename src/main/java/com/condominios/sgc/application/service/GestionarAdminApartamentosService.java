package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.ActualizarOcupantesCommand;
import com.condominios.sgc.application.dto.command.AsignarPropietarioCommand;
import com.condominios.sgc.application.dto.query.PaginaQuery;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.PaginaResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarAdminApartamentosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.InquilinoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.VehiculoRepositoryPort;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdminApartamentosService implements GestionarAdminApartamentosUseCase {

    private final ApartamentoRepositoryPort apartamentoRepository;
    private final InquilinoRepositoryPort inquilinoRepository;
    private final VehiculoRepositoryPort vehiculoRepository;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioIdResolver condominioIdResolver;

    public GestionarAdminApartamentosService(
            ApartamentoRepositoryPort apartamentoRepository,
            InquilinoRepositoryPort inquilinoRepository,
            VehiculoRepositoryPort vehiculoRepository,
            UsuarioRepositoryPort usuarioRepository,
            CondominioIdResolver condominioIdResolver) {
        this.apartamentoRepository = apartamentoRepository;
        this.inquilinoRepository = inquilinoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
        this.condominioIdResolver = condominioIdResolver;
    }

    @Override
    public PaginaResult<AdminApartamentoDetailResult> listar(Long condominioIdOverride, PaginaQuery pagina) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        return apartamentoRepository.buscarEnCondominio(condominioId, pagina);
    }

    @Override
    @Transactional
    public void asignarPropietario(Long condominioIdOverride, Long apartamentoId, AsignarPropietarioCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
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
    public void actualizarOcupantes(Long condominioIdOverride, Long apartamentoId, ActualizarOcupantesCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        if (!apartamentoRepository.existePorIdYCondominio(apartamentoId, condominioId)) {
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
}
