package br.com.totvs.desafio.infrastructure.config;

import java.util.Objects;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.application.service.CreateAccountImpl;
import br.com.totvs.desafio.application.service.CreateAccountsByFileImpl;
import br.com.totvs.desafio.application.service.GetAccountByIdImpl;
import br.com.totvs.desafio.application.service.GetAccountsToPayableImpl;
import br.com.totvs.desafio.application.service.GetTotalAmountPaidPerPeriodImpl;
import br.com.totvs.desafio.application.service.UpdateAccountImpl;
import br.com.totvs.desafio.application.service.UpdateAccountSituationImpl;

@Configuration
public class UseCaseConfig {

    private final AccountRepository accountRepository;

    public UseCaseConfig(AccountRepository accountRepository) {
        this.accountRepository = Objects.requireNonNull(accountRepository);
    }

    @Bean
    public CreateAccountImpl createAccountImpl() {
        return new CreateAccountImpl(accountRepository);
    }

    @Bean
    public UpdateAccountSituationImpl updateAccountSituationImpl() {
        return new UpdateAccountSituationImpl(accountRepository);
    }

    @Bean
    public UpdateAccountImpl updateAccountImpl() {
        return new UpdateAccountImpl(accountRepository);
    }

    @Bean
    public GetAccountByIdImpl getAccountByIdImpl() {
        return new GetAccountByIdImpl(accountRepository);
    }

    @Bean GetAccountsToPayableImpl getAccountToPayableImpl() {
        return new GetAccountsToPayableImpl(accountRepository);
    }

    @Bean GetTotalAmountPaidPerPeriodImpl getTotalAmountPaidPerPeriodImpl() {
        return new GetTotalAmountPaidPerPeriodImpl(accountRepository);
    }

    @Bean CreateAccountsByFileImpl createAccountsByFileImpl() {
        return new CreateAccountsByFileImpl(accountRepository);
    }

}