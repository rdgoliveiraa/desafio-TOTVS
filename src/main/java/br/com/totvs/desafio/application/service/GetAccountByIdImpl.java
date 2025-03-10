package br.com.totvs.desafio.application.service;

import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.application.excepetion.AccountNotFoundException;
import br.com.totvs.desafio.application.usecase.GetAccountByIdUseCase;

public class GetAccountByIdImpl implements GetAccountByIdUseCase {

    private final AccountRepository accountRepository;

    public GetAccountByIdImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account execute(Long id) {
        Account account = this.accountRepository.findById(id);
        if (account == null) {
            throw new AccountNotFoundException("Conta não encontrada");
        }
        return account;
    }

}
