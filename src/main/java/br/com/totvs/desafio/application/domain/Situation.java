package br.com.totvs.desafio.application.domain;

public enum Situation {

    PENDING,
    PAID,
    CANCELED;

    public static Situation fromString(String situation) {
        return Situation.valueOf(situation.toUpperCase());
    }

}