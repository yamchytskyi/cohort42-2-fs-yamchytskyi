package de.ait.javalessons.service;

import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;

    @Value("${bank.min-balance:0.0}")
    private double minBalance;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public Optional<BankAccount> findBankAccountById(Long id) {
        log.info("findBankAccountById: {}", id);
        return bankAccountRepository.findById(id);
    }

    public BankAccount saveNewBankAccount(BankAccount bankAccount) {
        log.info("saveNewBankAccount: {}", bankAccount);
        return bankAccountRepository.save(bankAccount);
    }

    @Transactional
    public double deposit(double amount, Long bankAccountId){
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Bank account with id " + bankAccountId + " not found"));
        if(amount <= 0){
            log.error("Amount must be greater than 0");
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        bankAccount.setBalance(bankAccount.getBalance() + amount);
        BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
        return savedBankAccount.getBalance();
    }

    @Transactional
    public double withdraw(double amount, Long bankAccountId){
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Bank account with id " + bankAccountId + " not found"));
        if(amount <= 0){
            log.error("Amount must be greater than 0");
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if(amount > bankAccount.getBalance()){
            log.error("Amount is greater than the current balance");
            throw new IllegalArgumentException("Amount is greater than the current balance");
        }
        if(bankAccount.getBalance() - amount < minBalance) {
            log.error("The current balance is less than min balance");
            throw new IllegalArgumentException("The current balance is less than min balance");
        }
        else {
            bankAccount.setBalance(bankAccount.getBalance() - amount);
            BankAccount savedBankAccount = bankAccountRepository.save(bankAccount);
            return savedBankAccount.getBalance();
        }
    }
}
