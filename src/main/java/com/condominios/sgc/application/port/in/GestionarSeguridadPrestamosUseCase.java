package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.RegistrarPrestamoCarritoCommand;
import com.condominios.sgc.application.dto.result.SecurityActiveCartLoanResult;

public interface GestionarSeguridadPrestamosUseCase {
    List<SecurityActiveCartLoanResult> listarActivos(Long condominioIdOverride);
    SecurityActiveCartLoanResult registrarPrestamo(Long condominioIdOverride, RegistrarPrestamoCarritoCommand cmd);
    void registrarDevolucion(Long id);
}
