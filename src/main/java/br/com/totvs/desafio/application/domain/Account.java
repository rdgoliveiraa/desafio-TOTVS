package br.com.totvs.desafio.application.domain;

import java.util.Date;

public class Account {

    private Long id;
    private Date dueDate;
    private Date paymentDate;
    private Double value;
    private String description;
    private Situation situation;

    public Account() {}

    public Account(Long id, Date dueDate, Date paymentDate, Double valor, String description,
            Situation situation) {
        if(valor < 0) {
            throw new IllegalArgumentException("O valor da conta não pode ser negativo");
        }
        this.id = id;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.value = valor;
        this.description = description;
        this.situation = situation;
    }

    public Account(Date dueDate, Date paymentDate, Double valor, String description,
            Situation situation) {
        if(valor < 0) {
            throw new IllegalArgumentException("O valor da conta não pode ser negativo");
        }
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.value = valor;
        this.description = description;
        this.situation = situation;
    }

    public Account(Long id, Date dueDate, Date paymentDate, Double valor,
            String description) {
        if(valor < 0) {
            throw new IllegalArgumentException("O valor da conta não pode ser negativo");
        }
        this.id = id;
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
        this.value = valor;
        this.description = description;
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

    public void setValue(Double valor) {
        this.value = valor;
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

    public void updateSituation(Situation situation) {
        this.situation = situation;
    }

}