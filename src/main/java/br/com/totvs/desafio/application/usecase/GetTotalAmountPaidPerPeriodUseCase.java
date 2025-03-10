package br.com.totvs.desafio.application.usecase;

import java.util.Date;

public interface GetTotalAmountPaidPerPeriodUseCase {

    public Double execute(Date startDate, Date endDate);
}
