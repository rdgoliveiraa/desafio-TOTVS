package br.com.totvs.desafio.application.service;

import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.application.domain.Situation;
import br.com.totvs.desafio.application.usecase.UpdateAccountSituationUseCase;

public class UpdateAccountSituationImpl implements UpdateAccountSituationUseCase {

    public final AccountRepository accountRepository;

    public UpdateAccountSituationImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account execute(Long id, Situation situation) {
        Account account = this.accountRepository.findById(id);
        account.updateSituation(situation);
        return this.accountRepository.save(account);
    }

}
