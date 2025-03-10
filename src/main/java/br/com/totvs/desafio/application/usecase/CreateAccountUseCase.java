package br.com.totvs.desafio.application.usecase;

import br.com.totvs.desafio.application.domain.Account;

public interface CreateAccountUseCase {

    public Account execute(Account account);
}
