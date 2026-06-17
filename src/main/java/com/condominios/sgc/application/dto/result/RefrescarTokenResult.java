package com.condominios.sgc.application.dto.result;

public record RefrescarTokenResult(
    String accessToken,
    String refreshToken,
    boolean recuerdame
) {}
