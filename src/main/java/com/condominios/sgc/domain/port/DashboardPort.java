package com.condominios.sgc.domain.port;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.CondominioModel;
import com.condominios.sgc.domain.model.InquilinoModel;
import com.condominios.sgc.domain.model.LogAccesoVehicularModel;
import com.condominios.sgc.domain.model.LogPrestamoCarritoModel;
import com.condominios.sgc.domain.model.UsuarioModel;

public interface DashboardPort {
    int contarCondominios();
    int contarApartamentos();

    int contarUsuariosPorRol(Rol rol);
    int contarTodosLosUsuario();
    int contarUsuariosPorActivo(Boolean activo);

    int contarUsuariosPorRolYCondominio(Rol rol, Long idCondominio);
    int contarApartamentosPorCondominio(Long idCondominio);
    int contarApartamentosOcupadosPorCondominio(Long idCondominio);
    int contarApartamentosDisponiblesPorCondominio(Long idCondominio);
    int contarTorresPorCondominio(Long idCondominio);
    int contarInquilinosPorCondominio(Long idCondominio);
    int contarVehiculosPorCondominio(Long idCondominio);
    int contarCarritosPrestadosPorCondominio(Long idCondominio);
    int contarAccesosHoyPorCondominio(Long idCondominio);
    int contarVehiculosDentroPorCondominio(Long idCondominio);
    int contarEstacionamientosDisponiblesPorCondominio(Long idCondominio);

    int contarVehiculosPorPropietario(Long idPropietario);
    int contarInquilinosPorPropietario(Long idPropietario);
    int contarCarritosPrestadosPorPropietario(Long idPropietario);

    List<CondominioModel> obtenerUltimosCondominios(int limit);
    List<UsuarioModel> obtenerUltimosUsuarios(int limit);
    List<UsuarioModel> obtenerUltimosUsuariosPorCondominio(Long idCondominio, int limit);
    List<LogAccesoVehicularModel> obtenerUltimosAccesosPorCondominio(Long idCondominio, int limit);
    List<LogPrestamoCarritoModel> obtenerCarritosPrestadosAhoraPorCondominio(Long idCondominio, int limit);
    List<LogPrestamoCarritoModel> obtenerCarritosPrestadosAhoraPorPropietario(Long idPropietario, int limit);
    List<InquilinoModel> obtenerInquilinosPorPropietario(Long idPropietario);
    List<LogAccesoVehicularModel> obtenerAccesosHoyPorPropietario(Long idPropietario);

    Map<String, Long> contarUsuariosAgrupadosPorRol();
    Map<String, Long> contarUsuariosPorMes(Instant desde);
    Map<String, Long> contarInquilinosPorTorre(Long idCondominio);
    List<LogPrestamoCarritoModel> obtenerPrestamosPorPropietario(Long idPropietario);
    List<LogAccesoVehicularModel> obtenerAccesosVehicularesPorCondominioDesde(Long idCondominio, Instant desde);
}
