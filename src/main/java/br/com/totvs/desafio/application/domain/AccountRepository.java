package br.com.totvs.desafio.application.domain;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountRepository {

    Account save(Account account);

    Account findById(Long id);

    Account update(Account account);

    Page<Account> findByPaymentDateAndDescription(Date paymentDate, String description, Pageable pageable);

    Double findTotalPaymentByPeriod(Date startDate, Date endDate);
}