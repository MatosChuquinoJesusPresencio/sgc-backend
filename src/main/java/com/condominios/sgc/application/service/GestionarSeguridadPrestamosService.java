package com.condominios.sgc.application.service;

import java.util.List;

import com.condominios.sgc.application.dto.command.RegistrarPrestamoCarritoCommand;
import com.condominios.sgc.application.dto.result.SecurityActiveCartLoanResult;
import com.condominios.sgc.application.dto.result.SecurityCartLoanFullResult;
import com.condominios.sgc.application.helper.CondominioIdResolver;
import com.condominios.sgc.application.port.in.GestionarSeguridadPrestamosUseCase;
import com.condominios.sgc.application.port.out.ApartamentoRepositoryPort;
import com.condominios.sgc.application.port.out.CarritoRepositoryPort;
import com.condominios.sgc.application.port.out.LogPrestamoCarritoRepositoryPort;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.shared.exception.ApartamentoException;
import com.condominios.sgc.domain.shared.exception.CarritoException;
import com.condominios.sgc.domain.shared.exception.LogPrestamoCarritoException;
import com.condominios.sgc.domain.type.EstadoCarrito;

import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class GestionarSeguridadPrestamosService implements GestionarSeguridadPrestamosUseCase {

    private final CondominioIdResolver condominioIdResolver;
    private final CarritoRepositoryPort carritoRepository;
    private final ApartamentoRepositoryPort apartamentoRepository;
    private final LogPrestamoCarritoRepositoryPort logPrestamoRepository;

    public GestionarSeguridadPrestamosService(
            CondominioIdResolver condominioIdResolver,
            CarritoRepositoryPort carritoRepository,
            ApartamentoRepositoryPort apartamentoRepository,
            LogPrestamoCarritoRepositoryPort logPrestamoRepository) {
        this.condominioIdResolver = condominioIdResolver;
        this.carritoRepository = carritoRepository;
        this.apartamentoRepository = apartamentoRepository;
        this.logPrestamoRepository = logPrestamoRepository;
    }

    @Override
    public List<SecurityActiveCartLoanResult> listarActivos(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        return logPrestamoRepository.buscarActivosPorCondominio(condominioId)
            .stream()
            .map(this::toResult)
            .toList();
    }

    @Override
    public List<SecurityCartLoanFullResult> listarTodos(Long condominioIdOverride) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);
        return logPrestamoRepository.listarTodosPorCondominio(condominioId)
            .stream()
            .map(m -> {
                var codigo = carritoRepository.buscarPorId(m.getIdCarrito())
                    .map(c -> c.getCodigo())
                    .orElse(null);
                return new SecurityCartLoanFullResult(
                    m.getId(),
                    m.getSolicitante() != null ? m.getSolicitante().name() : null,
                    m.getNombreSolicitante(),
                    m.getDniSolicitante(),
                    codigo,
                    m.getIdApartamento(),
                    m.getIdCarrito(),
                    m.getFechaPrestamo(),
                    m.getFechaDevolucion(),
                    m.getPenalizacion()
                );
            })
            .toList();
    }

    @Override
    @Transactional
    public SecurityActiveCartLoanResult registrarPrestamo(Long condominioIdOverride, RegistrarPrestamoCarritoCommand cmd) {
        var condominioId = condominioIdResolver.resolver(condominioIdOverride);

        var carrito = carritoRepository.buscarPorCodigo(cmd.codigoCarrito())
            .or(() -> {
                try {
                    return carritoRepository.buscarPorId(Long.parseLong(cmd.codigoCarrito()));
                } catch (NumberFormatException e) {
                    return java.util.Optional.empty();
                }
            })
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

        Long idProp = null, idInq = null;
        if ("PROPIETARIO".equalsIgnoreCase(cmd.solicitante())) {
            idProp = cmd.idPropietario() != null ? cmd.idPropietario() : apto.getIdPropietario();
        } else {
            idInq = cmd.idInquilino() != null ? cmd.idInquilino() : null;
        }

        var prestamo = new LogPrestamoCarritoModel(
            cmd.nombreSolicitante(), cmd.dniSolicitante(),
            apto.getId(), carrito.getId(), idProp, idInq, condominioId);
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

    private SecurityActiveCartLoanResult toResult(LogPrestamoCarritoModel m) {
        var codigo = carritoRepository.buscarPorId(m.getIdCarrito())
            .map(c -> c.getCodigo())
            .orElse(null);
        return new SecurityActiveCartLoanResult(
            m.getId(), m.getNombreSolicitante(), m.getDniSolicitante(),
            codigo, m.getFechaPrestamo(), m.getPenalizacion());
    }
}
