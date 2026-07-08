package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.CrearNodeCommand;
import com.condominios.sgc.application.dto.result.AdminApartamentoResult;
import com.condominios.sgc.application.dto.result.AdminPisoResult;
import com.condominios.sgc.application.dto.result.AdminStructureResult;
import com.condominios.sgc.application.dto.result.AdminTorreResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarAdminEstructuraUseCase;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.ParametroInvalidoException;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarAdminEstructuraService implements GestionarAdminEstructuraUseCase {

    private static final String TIPO_TORRE = "TORRE";
    private static final String TIPO_PISO = "PISO";
    private static final String TIPO_APARTAMENTO = "APARTAMENTO";

    private final CondominioRepositoryPort condominioRepository;
    private final CondominioIdResolver condominioIdResolver;

    public GestionarAdminEstructuraService(
            CondominioRepositoryPort condominioRepository,
            CondominioIdResolver condominioIdResolver) {
        this.condominioRepository = condominioRepository;
        this.condominioIdResolver = condominioIdResolver;
    }

    @Override
    public AdminStructureResult obtenerEstructura(Long condominioIdOverride) {
        var condominio = cargarCondominio(condominioIdOverride);
        var torres = condominio.getTorres().stream()
            .map(t -> new AdminTorreResult(
                t.getId(), t.getNombre(),
                t.getPisos().stream()
                    .map(p -> new AdminPisoResult(
                        p.getId(), p.getNumero(),
                        p.getApartamentos().stream()
                            .map(a -> new AdminApartamentoResult(
                                a.getId(), a.getNumero(), a.getMetraje(),
                                a.getDerechoEstacionamiento(), a.getIdPropietario()
                            ))
                            .toList()
                    ))
                    .toList()
            ))
            .toList();
        return new AdminStructureResult(condominio.getId(), condominio.getNombre(), torres);
    }

    @Override
    @Transactional
    public void crearNodo(Long condominioIdOverride, CrearNodeCommand cmd) {
        var condominio = cargarCondominio(condominioIdOverride);
        switch (cmd.tipo()) {
            case TIPO_TORRE -> condominio.agregarTorre(cmd.nombre());
            case TIPO_PISO -> condominio.agregarPiso(cmd.nombreTorre(), cmd.numero());
            case TIPO_APARTAMENTO -> condominio.agregarApartamento(
                cmd.nombreTorre(), cmd.numeroPiso(), cmd.numeroApartamento(), cmd.metraje());
            default -> throw new ParametroInvalidoException("tipo de nodo inválido: " + cmd.tipo());
        }
        condominioRepository.guardar(condominio);
    }

    @Override
    @Transactional
    public void eliminarNodo(Long condominioIdOverride, Long id, String tipo) {
        var condominio = cargarCondominio(condominioIdOverride);
        boolean eliminado = switch (tipo) {
            case TIPO_TORRE -> condominio.eliminarTorre(id);
            case TIPO_PISO -> condominio.eliminarPiso(id);
            case TIPO_APARTAMENTO -> condominio.eliminarApartamento(id);
            default -> throw new ParametroInvalidoException("tipo de nodo inválido: " + tipo);
        };
        if (!eliminado) {
            throw new ParametroInvalidoException(
                "nodo " + tipo + " con id " + id + " no encontrado en el condominio");
        }
        condominioRepository.guardar(condominio);
    }

    private CondominioModel cargarCondominio(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        return condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
    }
}
