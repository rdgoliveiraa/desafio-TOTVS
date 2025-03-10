package br.com.totvs.desafio.infrastructure.inbound.account;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import br.com.totvs.desafio.application.domain.Account;
import br.com.totvs.desafio.application.domain.Situation;
import br.com.totvs.desafio.application.service.CreateAccountImpl;
import br.com.totvs.desafio.application.service.CreateAccountsByFileImpl;
import br.com.totvs.desafio.application.service.GetAccountByIdImpl;
import br.com.totvs.desafio.application.service.GetAccountsToPayableImpl;
import br.com.totvs.desafio.application.service.GetTotalAmountPaidPerPeriodImpl;
import br.com.totvs.desafio.application.service.UpdateAccountImpl;
import br.com.totvs.desafio.application.service.UpdateAccountSituationImpl;
import br.com.totvs.desafio.infrastructure.inbound.account.request.CreateAccountDTO;
import br.com.totvs.desafio.infrastructure.inbound.account.request.UpdateAccountDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account/v1")
public class AccountController {

    private final CreateAccountImpl createAccount;
    private final UpdateAccountImpl updateAccount;
    private final GetAccountByIdImpl getAccountById;
    private final UpdateAccountSituationImpl updateAccountSituation;
    private final GetAccountsToPayableImpl getAccountsToPayable;
    private final GetTotalAmountPaidPerPeriodImpl getTotalAmountPaidPerPeriod;
    private final CreateAccountsByFileImpl createAccountByFile;

    public AccountController(CreateAccountImpl createAccount, UpdateAccountImpl updateAccount,
            GetAccountByIdImpl getAccountById, UpdateAccountSituationImpl updateAccountSituation,
            GetAccountsToPayableImpl getAccountToPayable,
            GetTotalAmountPaidPerPeriodImpl getTotalAmountPaidPerPeriod,
            CreateAccountsByFileImpl createAccountsByFile) {
        this.createAccount = Objects.requireNonNull(createAccount);
        this.updateAccount = Objects.requireNonNull(updateAccount);
        this.getAccountById = Objects.requireNonNull(getAccountById);
        this.updateAccountSituation = Objects.requireNonNull(updateAccountSituation);
        this.getAccountsToPayable = Objects.requireNonNull(getAccountToPayable);
        this.getTotalAmountPaidPerPeriod = Objects.requireNonNull(getTotalAmountPaidPerPeriod);
        this.createAccountByFile = Objects.requireNonNull(createAccountsByFile);
    }

    @PostMapping()
    public ResponseEntity<Account> createAccount(@Valid @RequestBody CreateAccountDTO createAccountDTO) {
        Account account = new Account(createAccountDTO.dueDate(), createAccountDTO.paymentDate(),
                createAccountDTO.value(), createAccountDTO.description(),
                Situation.fromString(createAccountDTO.situation()));
        return new ResponseEntity<>(createAccount.execute(account), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long id,
            @RequestBody UpdateAccountDTO updateAccountDTO) {
        Account account = new Account(id, updateAccountDTO.dueDate(),
                updateAccountDTO.paymentDate(), updateAccountDTO.value(),
                updateAccountDTO.description());
        return new ResponseEntity<>(updateAccount.execute(id, account), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> findAccountById(@PathVariable Long id) {
        return new ResponseEntity<>(getAccountById.execute(id), HttpStatus.OK);
    }

    @PutMapping("/{id}/situation/{situation}")
    public ResponseEntity<Account> updateSituation(@PathVariable Long id, @PathVariable String situation) {
        return new ResponseEntity<>(updateAccountSituation.execute(id, Situation.fromString(situation)), HttpStatus.OK);
    }

    @GetMapping("/due-date-description")
    public Page<Account> getAccountByDueDateAndDescription(
            @PageableDefault(page = 0, size = 10) @SortDefault(sort = "name",
                    direction = Sort.Direction.DESC) @SortDefault(sort = "id",
                            direction = Sort.Direction.ASC) @RequestParam @DateTimeFormat(
                                    iso = DateTimeFormat.ISO.DATE) Date dueDate,
            @RequestParam String description, Pageable pageable) {
        return getAccountsToPayable.execute(dueDate, description, pageable);
    }

    @GetMapping("/total-paid-per-period")
    public ResponseEntity<Double> getTotalPaidPerPeriod(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return new ResponseEntity<>(getTotalAmountPaidPerPeriod.execute(startDate, endDate), HttpStatus.OK);
    }

    @PostMapping("by-file")
    public ResponseEntity<String> updateByFile(@RequestParam("file") MultipartFile file) {
        
        try (InputStream inputStream = file.getInputStream()) {
            createAccountByFile.execute(inputStream);
            return new ResponseEntity<>("Processo finalizado", HttpStatus.OK);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        
    }
    
}