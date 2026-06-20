package com.condominios.sgc.application.port.out.service;

public interface HashServicePort {
    String hashear(String contrasena);
    boolean verificar(String contrasena, String hash);
}
