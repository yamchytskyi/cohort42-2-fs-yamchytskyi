package de.ait.javalessons.controller;

import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.service.BankAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/accounts")
public class BankAccountController {

    private BankAccountService bankAccountService;

    public BankAccountController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountService.getAllBankAccounts();
        return ResponseEntity.ok(bankAccounts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getBankAccountById(@RequestParam Long id) {
        return bankAccountService.findBankAccountById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping
    public ResponseEntity<BankAccount> createBankAccount(@RequestParam String accountNumber, @RequestParam String ownerName) {
        BankAccount bankAccount = new BankAccount(accountNumber, ownerName, 0.0);
        BankAccount savedBankAccount = bankAccountService.saveNewBankAccount(bankAccount);
        if(savedBankAccount == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return  ResponseEntity.status(HttpStatus.CREATED).body(savedBankAccount);
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<Double> deposit(@RequestParam Long id, @RequestParam double amount) {
        return ResponseEntity.ok(bankAccountService.deposit(amount, id));
    }

    @PostMapping("/{id}/withdraw")
    public ResponseEntity<Double> withdraw(@RequestParam Long id, @RequestParam double amount) {
        return ResponseEntity.ok(bankAccountService.withdraw(amount, id));
    }
}
