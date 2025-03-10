package br.com.totvs.desafio.infrastructure.utils;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.springframework.util.StringUtils;
import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.Situation;

public class AccountImportCsv {

    private final static String HEADER = "due_date;payment_date;value;description;situation";
    
    private AccountImportCsv() {}
    
    public static List<Account> readFile(final InputStream fileInput) {
        var accounts = new ArrayList<Account>();

        try (var scanner = new Scanner(fileInput)){
            scanner.useDelimiter("\n");
            var header = scanner.nextLine();
            validateHeader(header);
            while(scanner.hasNext()) {
                addAccount(scanner.next(), accounts);
            }
        }
            
        return accounts;
    }
            
    private static void validateHeader(String header) {
        if (!StringUtils.hasText(header) || !HEADER.equals(header)) {
            throw new IllegalArgumentException("Arquivo inválido");
        }
    }

    private static void addAccount(String line, List<Account> accounts) {
        try {
            var fields = line.split(";");
            var dueDate = fields[0].isEmpty() ? null : Date.valueOf(fields[0]);
            var paymentDate = fields[1].isEmpty() ? null : Date.valueOf(fields[1]);
            var value = fields[2].isEmpty() ? null : Double.parseDouble(fields[2]);
            var description = fields[3].isEmpty() ? null : fields[3];
            var situation = fields[4].isEmpty() ? null : Situation.fromString(fields[4]);
        
            accounts.add(new Account(dueDate, paymentDate, value, description, situation));
        } catch (Exception e) {
            throw new IllegalArgumentException("Campos com formato inválido");
        }
    }
}