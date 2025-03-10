package br.com.totvs.desafio.infrastructure.inbound.account.request;

import java.util.Date;

public record UpdateAccountDTO(Date dueDate, Date paymentDate, Double value, String description) {}