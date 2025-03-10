package br.com.totvs.desafio.infrastructure.outbound.account.entity;

import java.util.Date;
import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.Situation;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "accounts")
public class JpaAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "payment_date")
    private Date paymentDate;

    @Column(name = "value")
    private Double value;

    @Column(name = "description")
    private String description;

    @Column(name = "situation")
    private Situation situation;

    public JpaAccountEntity() {}

    public JpaAccountEntity(Account account) {
        this.id = account.getId();
        this.dueDate = account.getDueDate();
        this.paymentDate = account.getPaymentDate();
        this.value = account.getValue();
        this.description = account.getDescription();
        this.situation = account.getSituation();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Situation getSituation() {
        return situation;
    }

    public void setSituation(Situation situation) {
        this.situation = situation;
    }
}