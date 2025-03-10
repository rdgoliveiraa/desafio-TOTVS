package br.com.totvs.desafio.infrastructure.handlers;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;

public record ResponseError(String message, HttpStatus httpStatus, LocalDateTime now) {

}
