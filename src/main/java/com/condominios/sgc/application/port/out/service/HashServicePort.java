package com.condominios.sgc.application.port.out.service;

public interface HashServicePort {
    boolean verificar(String contrasena, String hash);
    String hashear(String contrasena);
}
