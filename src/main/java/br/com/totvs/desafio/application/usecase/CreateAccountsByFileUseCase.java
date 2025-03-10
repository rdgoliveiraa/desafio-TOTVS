package br.com.totvs.desafio.application.usecase;

import java.io.InputStream;

public interface CreateAccountsByFileUseCase {
    
    public void execute(InputStream inputStream);
}
