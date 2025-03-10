package br.com.totvs.desafio.infrastructure.inbound.authentication.response;

import java.util.Date;

public record TokenDTO(
        String username,
        Boolean authenticated,
        Date created,
        Date expiration,
        String accessToken,
        String refreshToken
    ) {
}
