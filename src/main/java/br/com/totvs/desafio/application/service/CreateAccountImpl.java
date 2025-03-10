package br.com.totvs.desafio.application.service;

import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.application.usecase.CreateAccountUseCase;

public class CreateAccountImpl implements CreateAccountUseCase {


    private final AccountRepository accountRepository;
    
    public CreateAccountImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account execute(Account account) {
        return this.accountRepository.save(account);
    }

}
