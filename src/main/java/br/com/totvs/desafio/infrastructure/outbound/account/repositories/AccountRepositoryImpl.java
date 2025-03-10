package br.com.totvs.desafio.infrastructure.outbound.account.repositories;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.AccountRepository;
import br.com.totvs.desafio.infrastructure.outbound.account.entity.JpaAccountEntity;

@Component
public class AccountRepositoryImpl implements AccountRepository {
    
    
    private final JpaAccountRepository jpaAccountRepository;

    public AccountRepositoryImpl(JpaAccountRepository jpaAccountRepository) {
        this.jpaAccountRepository = Objects.requireNonNull(jpaAccountRepository);
    }

    @Override
    public Account save(Account account) {
        JpaAccountEntity jpaAccountEntity = new JpaAccountEntity(account);
        this.jpaAccountRepository.save(jpaAccountEntity);
        return new Account(jpaAccountEntity.getId(), jpaAccountEntity.getDueDate(), jpaAccountEntity.getPaymentDate(), jpaAccountEntity.getValue(), jpaAccountEntity.getDescription(), jpaAccountEntity.getSituation());
    }

    @Override
    public Account findById(Long id) {
        Optional<JpaAccountEntity> jpaAccountEntity = this.jpaAccountRepository.findById(id);
        return jpaAccountEntity.map(account -> new Account(account.getId(), account.getDueDate(), account.getPaymentDate(), account.getValue(), account.getDescription(), account.getSituation())).orElse(null);
    }

    @Override
    public Account update(Account account) {
        Optional<JpaAccountEntity> jpaAccountEntity = this.jpaAccountRepository.findById(account.getId());
        if(jpaAccountEntity.isPresent()) {
            JpaAccountEntity jpaAccountEntityToUpdate = jpaAccountEntity.get();
            jpaAccountEntityToUpdate.setDueDate(account.getDueDate());
            jpaAccountEntityToUpdate.setPaymentDate(account.getPaymentDate());
            jpaAccountEntityToUpdate.setValue(account.getValue());
            jpaAccountEntityToUpdate.setDescription(account.getDescription());
            jpaAccountEntityToUpdate.setSituation(account.getSituation());
            this.jpaAccountRepository.save(jpaAccountEntityToUpdate);
        }
        return new Account(account.getId(), account.getDueDate(), account.getPaymentDate(), account.getValue(), account.getDescription(), account.getSituation());
    }

    @Override
    public Page<Account> findByPaymentDateAndDescription(Date dueDate, String description, Pageable pageable) {
        Page<JpaAccountEntity> jpaAccountEntities = this.jpaAccountRepository.findByDueDateAndDescription(dueDate, description, pageable);
        return jpaAccountEntities.map(account -> new Account(account.getId(), account.getDueDate(), account.getPaymentDate(), account.getValue(), account.getDescription(), account.getSituation()));
    }

    @Override
    public Double findTotalPaymentByPeriod(Date startDate, Date endDate) {
        Double total = this.jpaAccountRepository.findTotalPaidPerPeriod(startDate, endDate);
        if (total == null) {
            total = 0.0;
        }
        return total;
    }
    

}