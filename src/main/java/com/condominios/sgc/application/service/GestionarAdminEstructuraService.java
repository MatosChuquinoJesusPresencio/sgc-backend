package com.condominios.sgc.application.service;

import com.condominios.sgc.application.dto.command.CrearNodeCommand;
import com.condominios.sgc.application.dto.result.AdminApartamentoResult;
import com.condominios.sgc.application.dto.result.AdminPisoResult;
import com.condominios.sgc.application.dto.result.AdminStructureResult;
import com.condominios.sgc.application.dto.result.AdminTorreResult;
import com.condominios.sgc.application.port.in.GestionarAdminEstructuraUseCase;
import com.condominios.sgc.application.port.out.CondominioRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.shared.exception.CondominioException;
import com.condominios.sgc.domain.shared.exception.ParametroInvalidoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;

public class GestionarAdminEstructuraService implements GestionarAdminEstructuraUseCase {

    private static final String TIPO_TORRE = "TORRE";
    private static final String TIPO_PISO = "PISO";
    private static final String TIPO_APARTAMENTO = "APARTAMENTO";

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CondominioRepositoryPort condominioRepository;

    public GestionarAdminEstructuraService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CondominioRepositoryPort condominioRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.condominioRepository = condominioRepository;
    }

    @Override
    public AdminStructureResult obtenerEstructura() {
        var condominio = cargarCondominio();
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
    public void crearNodo(CrearNodeCommand cmd) {
        var condominio = cargarCondominio();
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
    public void eliminarNodo(Long id, String tipo) {
        var condominio = cargarCondominio();
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

    private CondominioModel cargarCondominio() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        var condominioId = usuario.getIdCondominio();
        if (condominioId == null) {
            throw CondominioException.noEncontrado();
        }
        return condominioRepository.buscarPorId(condominioId)
            .orElseThrow(CondominioException::noEncontrado);
    }
}
