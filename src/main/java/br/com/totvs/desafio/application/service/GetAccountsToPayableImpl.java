package br.com.totvs.desafio.application.service;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.application.usecase.GetAccountsToPayableUseCase;

public class GetAccountsToPayableImpl implements GetAccountsToPayableUseCase {

    private final AccountRepository accountRepository;

    public GetAccountsToPayableImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public Page<Account> execute(Date dueDate, String description, Pageable pageable) {
        return this.accountRepository.findByPaymentDateAndDescription(dueDate, description, pageable);
    }

}
