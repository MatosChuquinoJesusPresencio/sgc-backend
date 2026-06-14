package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.auxiliar.TipoDocumento;
import com.condominios.sgc.domain.exception.InquilinoException;
import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class InquilinoModel {
    private Long id;
    private String nombres;
    private String apellidos;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private Long idApartamento;

    public InquilinoModel(Long id, String nombres, String apellidos, TipoDocumento tipoDocumento,
            String numeroDocumento, Long idApartamento) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.idApartamento = idApartamento;
    }

    public InquilinoModel(String nombres, String apellidos, TipoDocumento tipoDocumento,
            String numeroDocumento, Long idApartamento) {
        this.id = null;
        this.nombres = requerido(nombres, InquilinoException::nombreRequerido);
        this.apellidos = requerido(apellidos, InquilinoException::apellidoRequerido);
        this.tipoDocumento = noNulo(tipoDocumento, InquilinoException::tipoDocumentoRequerido);
        this.numeroDocumento = requerido(numeroDocumento, InquilinoException::numeroDocumentoRequerido);
        this.idApartamento = noNulo(idApartamento, InquilinoException::apartamentoRequerido);
    }

    public Long getId() { return id; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public TipoDocumento getTipoDocumento() { return tipoDocumento; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public Long getIdApartamento() { return idApartamento; }

    public void asignarApartamento(Long idApartamento) {
        this.idApartamento = noNulo(idApartamento, InquilinoException::apartamentoRequerido);
    }

    public void desasignarApartamento() {
        this.idApartamento = null;
    }

    public void actualizar(String nombres, String apellidos, TipoDocumento tipoDocumento, String numeroDocumento) {
        this.nombres = requerido(nombres, InquilinoException::nombreRequerido);
        this.apellidos = requerido(apellidos, InquilinoException::apellidoRequerido);
        this.tipoDocumento = noNulo(tipoDocumento, InquilinoException::tipoDocumentoRequerido);
        this.numeroDocumento = requerido(numeroDocumento, InquilinoException::numeroDocumentoRequerido);
    }
}
