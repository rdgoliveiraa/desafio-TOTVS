package br.com.totvs.desafio.application.usecase;

import br.com.totvs.desafio.application.domain.Account;

public interface GetAccountByIdUseCase {

    public Account execute(Long id);
}
