package br.com.totvs.desafio.application.usecase;

import br.com.totvs.desafio.application.domain.Account;

public interface UpdateAccountUseCase {
    
    public Account execute(Long id, Account account);
}