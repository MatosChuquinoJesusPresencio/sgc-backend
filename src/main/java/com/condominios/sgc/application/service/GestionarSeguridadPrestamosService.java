package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.command.RegistrarPrestamoCarritoCommand;
import com.condominios.sgc.application.dto.result.SecurityActiveCartLoanResult;
import com.condominios.sgc.application.port.in.GestionarSeguridadPrestamosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.application.port.out.UsuarioRepositoryPort;
import com.condominios.sgc.application.port.out.service.SecurityServicePort;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.CarritoException;
import com.condominios.sgc.domain.shared.exception.LogPrestamoCarritoException;
import com.condominios.sgc.domain.shared.exception.UsuarioException;
import com.condominios.sgc.domain.type.EstadoCarrito;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarSeguridadPrestamosService implements GestionarSeguridadPrestamosUseCase {

    private final SecurityServicePort securityService;
    private final UsuarioRepositoryPort usuarioRepository;
    private final CarritoRepositoryPort carritoRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final LogPrestamoCarritoRepositoryPort logPrestamoRepository;

    public GestionarSeguridadPrestamosService(
            SecurityServicePort securityService,
            UsuarioRepositoryPort usuarioRepository,
            CarritoRepositoryPort carritoRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            LogPrestamoCarritoRepositoryPort logPrestamoRepository) {
        this.securityService = securityService;
        this.usuarioRepository = usuarioRepository;
        this.carritoRepository = carritoRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.logPrestamoRepository = logPrestamoRepository;
    }

    @Override
    public List<SecurityActiveCartLoanResult> listarActivos() {
        var condominioId = obtenerCondominioId();
        return logPrestamoRepository.buscarActivosPorCondominio(condominioId)
            .stream()
            .map(this::toResult)
            .toList();
    }

    @Override
    @Transactional
    public SecurityActiveCartLoanResult registrarPrestamo(RegistrarPrestamoCarritoCommand cmd) {
        var condominioId = obtenerCondominioId();

        var carrito = carritoRepository.buscarPorCodigo(cmd.codigoCarrito())
            .orElseThrow(CarritoException::noEncontrado);
        if (!carrito.getIdCondominio().equals(condominioId)) {
            throw CarritoException.noEncontrado();
        }
        if (carrito.getEstado() != EstadoCarrito.DISPONIBLE) {
            throw CarritoException.estadoInvalido();
        }
        carrito.actualizarEstado(EstadoCarrito.EN_USO);
        carritoRepository.guardar(carrito);

        var apto = apartamentoRepository.buscarPorNumeroYCondominio(cmd.numeroApartamento(), condominioId)
            .orElseThrow(ApartamentoException::noEncontrado);

        var prestamo = new LogPrestamoCarritoModel(
            cmd.nombreSolicitante(), cmd.dniSolicitante(),
            apto.getId(), carrito.getId(), null, null, condominioId);
        var guardado = logPrestamoRepository.guardar(prestamo);
        return toResult(guardado);
    }

    @Override
    @Transactional
    public void registrarDevolucion(Long id) {
        var prestamo = logPrestamoRepository.buscarPorId(id)
            .orElseThrow(LogPrestamoCarritoException::noEncontrado);
        prestamo.registrarDevolucion(prestamo.getPenalizacion());
        logPrestamoRepository.guardar(prestamo);

        var carrito = carritoRepository.buscarPorId(prestamo.getIdCarrito())
            .orElseThrow(CarritoException::noEncontrado);
        carrito.actualizarEstado(EstadoCarrito.DISPONIBLE);
        carritoRepository.guardar(carrito);
    }

    private Long obtenerCondominioId() {
        var usuario = usuarioRepository.buscarPorId(securityService.obtenerIdUsuario())
            .orElseThrow(UsuarioException::noEncontrado);
        return usuario.getIdCondominio();
    }

    private SecurityActiveCartLoanResult toResult(LogPrestamoCarritoModel m) {
        var codigo = carritoRepository.buscarPorId(m.getIdCarrito())
            .map(c -> c.getCodigo())
            .orElse(null);
        return new SecurityActiveCartLoanResult(
            m.getId(), m.getNombreSolicitante(), m.getDniSolicitante(),
            codigo, m.getFechaPrestamo(), m.getPenalizacion());
    }
}
