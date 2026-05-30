package com.condominios.sgc.application.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.condominios.sgc.application.dto.IniciarPrestamoRequest;
import com.condominios.sgc.application.usecase.FinalizarPrestamoCarritoUseCase;
import com.condominios.sgc.application.usecase.IniciarPrestamoCarritoUseCase;
import com.condominios.sgc.application.usecase.ListarLogsPrestamoPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ListarLogsPrestamoPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerLogPrestamoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;

@Service
public class LogPrestamoCarritoService {

    private final IniciarPrestamoCarritoUseCase iniciarPrestamoCarritoUseCase;
    private final FinalizarPrestamoCarritoUseCase finalizarPrestamoCarritoUseCase;
    private final ObtenerLogPrestamoUseCase obtenerLogPrestamoUseCase;
    private final ListarLogsPrestamoPorApartamentoUseCase listarLogsPrestamoPorApartamentoUseCase;
    private final ListarLogsPrestamoPorCondominioUseCase listarLogsPrestamoPorCondominioUseCase;

    public LogPrestamoCarritoService(
            IniciarPrestamoCarritoUseCase iniciarPrestamoCarritoUseCase,
            FinalizarPrestamoCarritoUseCase finalizarPrestamoCarritoUseCase,
            ObtenerLogPrestamoUseCase obtenerLogPrestamoUseCase,
            ListarLogsPrestamoPorApartamentoUseCase listarLogsPrestamoPorApartamentoUseCase,
            ListarLogsPrestamoPorCondominioUseCase listarLogsPrestamoPorCondominioUseCase) {
        this.iniciarPrestamoCarritoUseCase = iniciarPrestamoCarritoUseCase;
        this.finalizarPrestamoCarritoUseCase = finalizarPrestamoCarritoUseCase;
        this.obtenerLogPrestamoUseCase = obtenerLogPrestamoUseCase;
        this.listarLogsPrestamoPorApartamentoUseCase = listarLogsPrestamoPorApartamentoUseCase;
        this.listarLogsPrestamoPorCondominioUseCase = listarLogsPrestamoPorCondominioUseCase;
    }

    public LogPrestamoCarritoModel iniciar(IniciarPrestamoRequest request) {
        return iniciarPrestamoCarritoUseCase.ejecutar(request);
    }

    public LogPrestamoCarritoModel finalizar(Long logId, BigDecimal penalizacion) {
        return finalizarPrestamoCarritoUseCase.ejecutar(logId, penalizacion);
    }

    public LogPrestamoCarritoModel obtener(Long id) {
        return obtenerLogPrestamoUseCase.ejecutar(id);
    }

    public PaginacionResponse<LogPrestamoCarritoModel> listarPorApartamento(Long apartamentoId, PaginacionRequest request) {
        return listarLogsPrestamoPorApartamentoUseCase.ejecutar(apartamentoId, request);
    }

    public PaginacionResponse<LogPrestamoCarritoModel> listarPorCondominio(Long condominioId, PaginacionRequest request) {
        return listarLogsPrestamoPorCondominioUseCase.ejecutar(condominioId, request);
    }
}
