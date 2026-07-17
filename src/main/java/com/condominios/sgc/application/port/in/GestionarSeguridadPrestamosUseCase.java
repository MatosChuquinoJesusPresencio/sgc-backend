package com.condominios.sgc.application.port.in;

import java.util.List;

import com.condominios.sgc.application.dto.command.RegistrarPrestamoCarritoCommand;
import com.condominios.sgc.application.dto.result.AdminApartamentoDetailResult;
import com.condominios.sgc.application.dto.result.SecurityActiveCartLoanResult;
import com.condominios.sgc.application.dto.result.SecurityCartLoanFullResult;
import com.condominios.sgc.domain.model.CarritoModel;

public interface GestionarSeguridadPrestamosUseCase {
    List<SecurityActiveCartLoanResult> listarActivos(Long condominioIdOverride);
    SecurityActiveCartLoanResult registrarPrestamo(Long condominioIdOverride, RegistrarPrestamoCarritoCommand cmd);
    void registrarDevolucion(Long id);
    List<SecurityCartLoanFullResult> listarTodos(Long condominioIdOverride);
    List<CarritoModel> listarCarritos(Long condominioIdOverride);
    List<AdminApartamentoDetailResult> listarApartamentos(Long condominioIdOverride);
    void actualizarEstadoCarrito(Long id, String estado);
}
