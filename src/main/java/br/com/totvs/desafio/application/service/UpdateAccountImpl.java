package br.com.totvs.desafio.application.service;

import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.application.usecase.UpdateAccountUseCase;

public class UpdateAccountImpl implements UpdateAccountUseCase {

    private final AccountRepository accountRepository;

    public UpdateAccountImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account execute(Long id, Account account) {
        Account existAccount = this.accountRepository.findById(id);
        existAccount.setDescription(account.getDescription());
        existAccount.setDueDate(account.getDueDate());
        existAccount.setPaymentDate(account.getPaymentDate());
        existAccount.setValue(account.getValue());
        return this.accountRepository.save(existAccount);
    }

}
