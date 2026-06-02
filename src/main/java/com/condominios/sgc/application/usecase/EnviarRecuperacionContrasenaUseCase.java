package com.condominios.sgc.application.usecase;

import java.util.Optional;

public interface EnviarRecuperacionContrasenaUseCase {
    Optional<String> ejecutar(String email, String token);
}
