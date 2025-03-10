package br.com.totvs.desafio.application.service;

import java.util.Date;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.application.usecase.GetTotalAmountPaidPerPeriodUseCase;

public class GetTotalAmountPaidPerPeriodImpl implements GetTotalAmountPaidPerPeriodUseCase {

    private final AccountRepository accountRepository;

    public GetTotalAmountPaidPerPeriodImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    
    @Override
    public Double execute(Date startDate, Date endDate) {
        return this.accountRepository.findTotalPaymentByPeriod(startDate, endDate);
    }

}
