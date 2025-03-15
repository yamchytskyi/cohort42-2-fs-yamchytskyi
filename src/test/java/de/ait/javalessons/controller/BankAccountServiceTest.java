package de.ait.javalessons.controller;

import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.service.BankAccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BankAccountServiceTest {

    @Value("${bank.min-balance:0.0}")
    private double minBalance;

}
