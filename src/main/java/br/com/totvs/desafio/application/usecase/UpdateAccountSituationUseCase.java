package br.com.totvs.desafio.application.usecase;

import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.Situation;

public interface UpdateAccountSituationUseCase {

    Account execute(Long id, Situation situation);
}