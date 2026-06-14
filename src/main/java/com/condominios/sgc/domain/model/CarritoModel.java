package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.auxiliar.EstadoCarrito;
import com.condominios.sgc.domain.exception.CarritoException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class CarritoModel {
    private Long id;
    private String codigo;
    private EstadoCarrito estado;
    private Long idCondominio;

    public CarritoModel(Long id, String codigo, EstadoCarrito estado, Long idCondominio) {
        this.id = id;
        this.codigo = codigo;
        this.estado = estado;
        this.idCondominio = idCondominio;
    }

    public CarritoModel(String codigo, Long idCondominio) {
        this.id = null;
        this.codigo = requerido(codigo, CarritoException::codigoRequerido);
        this.estado = EstadoCarrito.DISPONIBLE;
        this.idCondominio = noNulo(idCondominio, CarritoException::condominioRequerido);
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public EstadoCarrito getEstado() { return estado; }
    public Long getIdCondominio() { return idCondominio; }
    
    public void actualizarEstado(EstadoCarrito estado) {
        if (!this.estado.puedeCambiarA(estado)) {
            throw CarritoException.estadoInvalido();
        }
        this.estado = estado;
    }

    public void actualizarCodigo(String codigo) {
        this.codigo = requerido(codigo, CarritoException::codigoRequerido);
    }
}
