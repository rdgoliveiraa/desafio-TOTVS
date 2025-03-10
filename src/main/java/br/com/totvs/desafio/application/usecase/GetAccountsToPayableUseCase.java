package br.com.totvs.desafio.application.usecase;


import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.totvs.desafio.application.domain.Account;

public interface GetAccountsToPayableUseCase {
    
    public Page<Account> execute(Date dueDate, String description, Pageable pageable);
}
