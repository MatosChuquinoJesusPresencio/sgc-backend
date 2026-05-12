package com.condominios.sgc.application.dto;

public class CrearVehiculoRequest {
    private String marca;
    private String color;
    private String modelo;
    private String placa;

    public CrearVehiculoRequest() {
    }

    public CrearVehiculoRequest(String marca, String color, String modelo, String placa) {
        this.marca = marca;
        this.color = color;
        this.modelo = modelo;
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
