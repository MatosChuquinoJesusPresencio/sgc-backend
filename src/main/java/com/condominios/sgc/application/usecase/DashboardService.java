package com.condominios.sgc.application.usecase;

import com.condominios.sgc.application.dto.result.*;
import com.condominios.sgc.domain.auxiliar.Rol;
import com.condominios.sgc.domain.model.*;
import com.condominios.sgc.domain.port.DashboardPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardService {

    private final DashboardPort dashboardPort;

    public DashboardService(DashboardPort dashboardPort) {
        this.dashboardPort = dashboardPort;
    }

    public DashboardSAResult obtenerDashboardSA() {
        return new DashboardSAResult(
                dashboardPort.contarCondominios(),
                dashboardPort.contarUsuariosPorRol(Rol.ADMINISTRADOR_CONDOMINIO),
                dashboardPort.contarTodosLosUsuario(),
                dashboardPort.contarUsuariosPorActivo(true),
                dashboardPort.contarUsuariosPorActivo(false),
                dashboardPort.contarUsuariosPorRol(Rol.PROPIETARIO),
                dashboardPort.contarApartamentos(),
                aResumenCondominio(dashboardPort.obtenerUltimosCondominios(5)),
                aResumenUsuario(dashboardPort.obtenerUltimosUsuarios(5))
        );
    }

    public DashboardACResult obtenerDashboardAC(Long idCondominio) {
        return new DashboardACResult(
                dashboardPort.contarUsuariosPorRolYCondominio(Rol.PROPIETARIO, idCondominio),
                dashboardPort.contarApartamentosPorCondominio(idCondominio),
                dashboardPort.contarApartamentosOcupadosPorCondominio(idCondominio),
                dashboardPort.contarApartamentosDisponiblesPorCondominio(idCondominio),
                dashboardPort.contarTorresPorCondominio(idCondominio),
                dashboardPort.contarInquilinosPorCondominio(idCondominio),
                dashboardPort.contarVehiculosPorCondominio(idCondominio),
                dashboardPort.contarCarritosPrestadosPorCondominio(idCondominio),
                dashboardPort.contarAccesosHoyPorCondominio(idCondominio),
                aResumenLogAcceso(dashboardPort.obtenerUltimosAccesosPorCondominio(idCondominio, 5)),
                aResumenPrestamo(dashboardPort.obtenerCarritosPrestadosAhoraPorCondominio(idCondominio, 5)),
                aResumenUsuario(dashboardPort.obtenerUltimosUsuariosPorCondominio(idCondominio, 5))
        );
    }

    public DashboardPropResult obtenerDashboardProp(Long idPropietario) {
        List<InquilinoModel> inquilinos = dashboardPort.obtenerInquilinosPorPropietario(idPropietario);
        List<LogAccesoVehicularModel> accesosHoy = dashboardPort.obtenerAccesosHoyPorPropietario(idPropietario);

        return new DashboardPropResult(
                dashboardPort.contarVehiculosPorPropietario(idPropietario),
                dashboardPort.contarInquilinosPorPropietario(idPropietario),
                dashboardPort.contarCarritosPrestadosPorPropietario(idPropietario),
                aResumenInquilino(inquilinos),
                aResumenLogAcceso(accesosHoy)
        );
    }

    public DashboardASResult obtenerDashboardAS(Long idCondominio) {
        return new DashboardASResult(
                dashboardPort.contarAccesosHoyPorCondominio(idCondominio),
                dashboardPort.contarVehiculosDentroPorCondominio(idCondominio),
                dashboardPort.contarCarritosPrestadosPorCondominio(idCondominio),
                dashboardPort.contarEstacionamientosDisponiblesPorCondominio(idCondominio),
                aResumenLogAcceso(dashboardPort.obtenerUltimosAccesosPorCondominio(idCondominio, 5)),
                aResumenPrestamo(dashboardPort.obtenerCarritosPrestadosAhoraPorCondominio(idCondominio, 5))
        );
    }

    // Charts — SA
    public List<DatoGrafico> obtenerUsuariosPorRolSA() {
        return dashboardPort.contarUsuariosAgrupadosPorRol().entrySet().stream()
                .map(e -> new DatoGrafico(e.getKey(), e.getValue()))
                .toList();
    }

    public List<DatoGrafico> obtenerCrecimientoUsuariosSA(int meses) {
        Instant desde = Instant.now().minus(meses * 30L, ChronoUnit.DAYS);
        return dashboardPort.contarUsuariosPorMes(desde).entrySet().stream()
                .map(e -> new DatoGrafico(e.getKey(), e.getValue()))
                .toList();
    }

    // Charts — AC
    public List<DatoGrafico> obtenerInquilinosPorTorreAC(Long idCondominio) {
        return dashboardPort.contarInquilinosPorTorre(idCondominio).entrySet().stream()
                .map(e -> new DatoGrafico(e.getKey(), e.getValue()))
                .toList();
    }

    // Charts — Prop
    public List<DatoGrafico> obtenerCarritosPorMesProp(Long idPropietario, int meses) {
        Instant desde = Instant.now().minus(meses * 30L, ChronoUnit.DAYS);
        Map<String, Long> porMes = dashboardPort.obtenerPrestamosPorPropietario(idPropietario).stream()
                .filter(p -> p.getFechaPrestamo().isAfter(desde))
                .collect(Collectors.groupingBy(
                        p -> p.getFechaPrestamo().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy-MM")),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
        return porMes.entrySet().stream()
                .map(e -> new DatoGrafico(e.getKey(), e.getValue()))
                .toList();
    }

    public List<DatoGrafico> obtenerPenalizacionesPorMesProp(Long idPropietario, int meses) {
        Instant desde = Instant.now().minus(meses * 30L, ChronoUnit.DAYS);
        Map<String, BigDecimal> porMes = dashboardPort.obtenerPrestamosPorPropietario(idPropietario).stream()
                .filter(p -> p.getFechaPrestamo().isAfter(desde))
                .filter(p -> p.getPenalizacion() != null && p.getPenalizacion().compareTo(BigDecimal.ZERO) > 0)
                .collect(Collectors.groupingBy(
                        p -> p.getFechaPrestamo().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("yyyy-MM")),
                        LinkedHashMap::new,
                        Collectors.reducing(BigDecimal.ZERO, LogPrestamoCarritoModel::getPenalizacion, BigDecimal::add)
                ));
        return porMes.entrySet().stream()
                .map(e -> new DatoGrafico(e.getKey(), e.getValue()))
                .toList();
    }

    // Charts — AS
    public List<DatoGrafico> obtenerAccesosPorHoraAS(Long idCondominio) {
        Instant desde = Instant.now().minus(24, ChronoUnit.HOURS);
        Map<Integer, Long> porHora = dashboardPort.obtenerAccesosVehicularesPorCondominioDesde(idCondominio, desde)
                .stream()
                .collect(Collectors.groupingBy(
                        a -> a.getFechaEntrada().atZone(ZoneId.systemDefault()).getHour(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
        return porHora.entrySet().stream()
                .map(e -> new DatoGrafico(String.valueOf(e.getKey()), e.getValue()))
                .toList();
    }

    public List<DatoGrafico> obtenerTendenciaAccesosAS(Long idCondominio, int dias) {
        Instant desde = Instant.now().minus(dias, ChronoUnit.DAYS);
        Map<String, Long> porDia = dashboardPort.obtenerAccesosVehicularesPorCondominioDesde(idCondominio, desde)
                .stream()
                .collect(Collectors.groupingBy(
                        a -> a.getFechaEntrada().atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ISO_LOCAL_DATE),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));
        return porDia.entrySet().stream()
                .map(e -> new DatoGrafico(e.getKey(), e.getValue()))
                .toList();
    }

    private List<CondominioResumen> aResumenCondominio(List<CondominioModel> modelos) {
        return modelos.stream()
                .map(m -> new CondominioResumen(m.getId(), m.getNombre(), m.getDireccion(), m.getFechaCreacion()))
                .toList();
    }

    private List<UsuarioResumen> aResumenUsuario(List<UsuarioModel> modelos) {
        return modelos.stream()
                .map(m -> new UsuarioResumen(m.getId(), m.getNombres(), m.getApellidos(),
                        m.getCorreo(), m.getRol().name(), m.getActivo(), m.getFechaCreacion()))
                .toList();
    }

    private List<LogAccesoResumen> aResumenLogAcceso(List<LogAccesoVehicularModel> modelos) {
        return modelos.stream()
                .map(m -> new LogAccesoResumen(m.getId(), m.getPlaca(), m.getOcupante().name(),
                        m.getMetodo().name(), m.getFechaEntrada(), m.getFechaSalida()))
                .toList();
    }

    private List<CarritoPrestadoResumen> aResumenPrestamo(List<LogPrestamoCarritoModel> modelos) {
        return modelos.stream()
                .map(m -> new CarritoPrestadoResumen(m.getId(), String.valueOf(m.getIdCarrito()),
                        m.getNombreSolicitante(), m.getDniSolicitante(),
                        m.getFechaPrestamo(), m.getIdApartamento()))
                .toList();
    }

    private List<InquilinoResumen> aResumenInquilino(List<InquilinoModel> modelos) {
        return modelos.stream()
                .map(m -> new InquilinoResumen(m.getId(), m.getNombres(), m.getApellidos(),
                        m.getTipoDocumento().name(), m.getNumeroDocumento()))
                .toList();
    }
}
