package br.com.totvs.desafio.infrastructure.outbound.account.repositories;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import br.com.totvs.desafio.infrastructure.outbound.account.entity.JpaAccountEntity;

public interface JpaAccountRepository extends JpaRepository<JpaAccountEntity, Long> {

    Page<JpaAccountEntity> findByDueDateAndDescription(Date dueDate, String description, Pageable pageable);

    @NativeQuery("select sum(c.value) from Accounts c where c.situation = '1' and c.due_date between :startDate and :endDate")
    Double findTotalPaidPerPeriod(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}