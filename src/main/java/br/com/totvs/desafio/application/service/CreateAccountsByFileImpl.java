package br.com.totvs.desafio.application.service;

import java.io.InputStream;
import java.util.List;
import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.application.usecase.CreateAccountsByFileUseCase;
import br.com.totvs.desafio.infrastructure.utils.AccountImportCsv;

public class CreateAccountsByFileImpl implements CreateAccountsByFileUseCase {

    private final AccountRepository accountRepository;

    public CreateAccountsByFileImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public void execute(InputStream inputStream) {
        List<Account> accounts = AccountImportCsv.readFile(inputStream);
        accounts.forEach(this.accountRepository::save);
    }

}
