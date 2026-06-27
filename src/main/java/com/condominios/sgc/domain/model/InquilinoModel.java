package com.condominios.sgc.domain.model;

import com.condominios.sgc.domain.shared.exception.InquilinoException;
import com.condominios.sgc.domain.shared.valueobject.NombreCompleto;
import com.condominios.sgc.domain.shared.valueobject.NumeroDocumento;
import com.condominios.sgc.domain.type.TipoDocumento;

import static com.condominios.sgc.domain.util.ValidacionUtil.*;

public class InquilinoModel {
    private Long id;
    private NombreCompleto nombreCompleto;
    private NumeroDocumento numeroDocumento;
    private Long idApartamento;

    public InquilinoModel(Long id, NombreCompleto nombreCompleto, NumeroDocumento numeroDocumento,
            Long idApartamento) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.numeroDocumento = numeroDocumento;
        this.idApartamento = idApartamento;
    }

    public InquilinoModel(String nombres, String apellidos,
            TipoDocumento tipoDocumento, String numeroDocumento, Long idApartamento) {
        this.id = null;
        this.nombreCompleto = new NombreCompleto(nombres, apellidos);
        this.numeroDocumento = new NumeroDocumento(tipoDocumento, numeroDocumento);
        this.idApartamento = noNulo(idApartamento, InquilinoException::apartamentoRequerido);
    }

    public Long getId() { return id; }
    public NombreCompleto getNombreCompleto() { return nombreCompleto; }
    public String getNombres() { return nombreCompleto.nombres(); }
    public String getApellidos() { return nombreCompleto.apellidos(); }
    public NumeroDocumento getNumeroDocumento() { return numeroDocumento; }
    public Long getIdApartamento() { return idApartamento; }

    public void asignarApartamento(Long idApartamento) {
        this.idApartamento = noNulo(idApartamento, InquilinoException::apartamentoRequerido);
    }

    public void actualizar(String nombres, String apellidos, TipoDocumento tipoDocumento, String numeroDocumento) {
        this.nombreCompleto = new NombreCompleto(nombres, apellidos);
        this.numeroDocumento = new NumeroDocumento(tipoDocumento, numeroDocumento);
    }
}
