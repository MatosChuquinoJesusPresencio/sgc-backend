package com.condominios.sgc.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.condominios.sgc.application.dto.RegistrarEntradaRequest;
import com.condominios.sgc.application.usecase.ListarLogsAccesoPorApartamentoUseCase;
import com.condominios.sgc.application.usecase.ListarLogsAccesoPorCondominioUseCase;
import com.condominios.sgc.application.usecase.ObtenerLogAccesoUseCase;
import com.condominios.sgc.application.usecase.RegistrarEntradaVehiculoUseCase;
import com.condominios.sgc.application.usecase.RegistrarSalidaVehiculoUseCase;
import com.condominios.sgc.domain.dto.PaginacionRequest;
import com.condominios.sgc.domain.dto.PaginacionResponse;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;

@Service
@Transactional
public class LogAccesoVehicularService {

    private final RegistrarEntradaVehiculoUseCase registrarEntradaVehiculoUseCase;
    private final RegistrarSalidaVehiculoUseCase registrarSalidaVehiculoUseCase;
    private final ObtenerLogAccesoUseCase obtenerLogAccesoUseCase;
    private final ListarLogsAccesoPorApartamentoUseCase listarLogsAccesoPorApartamentoUseCase;
    private final ListarLogsAccesoPorCondominioUseCase listarLogsAccesoPorCondominioUseCase;

    public LogAccesoVehicularService(
            RegistrarEntradaVehiculoUseCase registrarEntradaVehiculoUseCase,
            RegistrarSalidaVehiculoUseCase registrarSalidaVehiculoUseCase,
            ObtenerLogAccesoUseCase obtenerLogAccesoUseCase,
            ListarLogsAccesoPorApartamentoUseCase listarLogsAccesoPorApartamentoUseCase,
            ListarLogsAccesoPorCondominioUseCase listarLogsAccesoPorCondominioUseCase) {
        this.registrarEntradaVehiculoUseCase = registrarEntradaVehiculoUseCase;
        this.registrarSalidaVehiculoUseCase = registrarSalidaVehiculoUseCase;
        this.obtenerLogAccesoUseCase = obtenerLogAccesoUseCase;
        this.listarLogsAccesoPorApartamentoUseCase = listarLogsAccesoPorApartamentoUseCase;
        this.listarLogsAccesoPorCondominioUseCase = listarLogsAccesoPorCondominioUseCase;
    }

    public LogAccesoVehicularModel registrarEntrada(RegistrarEntradaRequest request) {
        return registrarEntradaVehiculoUseCase.ejecutar(request);
    }

    public LogAccesoVehicularModel registrarSalida(Long logId) {
        return registrarSalidaVehiculoUseCase.ejecutar(logId);
    }

    public LogAccesoVehicularModel obtener(Long id) {
        return obtenerLogAccesoUseCase.ejecutar(id);
    }

    public PaginacionResponse<LogAccesoVehicularModel> listarPorApartamento(Long apartamentoId, PaginacionRequest request) {
        return listarLogsAccesoPorApartamentoUseCase.ejecutar(apartamentoId, request);
    }

    public PaginacionResponse<LogAccesoVehicularModel> listarPorCondominio(Long condominioId, PaginacionRequest request) {
        return listarLogsAccesoPorCondominioUseCase.ejecutar(condominioId, request);
    }
}
