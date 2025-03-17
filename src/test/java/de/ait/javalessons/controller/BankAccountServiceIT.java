package de.ait.javalessons.controller;

import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountServiceIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    private static final String BASE_URL = "/accounts";

    private List<BankAccount> testBankAccounts = new ArrayList<>();

    @BeforeEach
    void setUp() {
        testBankAccounts.clear();
    }

    @AfterEach
    void deleteTestData() {
        for(BankAccount bankAccount : testBankAccounts) {
            bankAccountRepository.deleteById(bankAccount.getId());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1001, Bob Smith"
    })
    void testGetAllBankAccounts(String accountNumber, String ownerName) {
        ResponseEntity<BankAccount[]> response = testRestTemplate.getForEntity(BASE_URL, BankAccount[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody().length);
        assertEquals(accountNumber, response.getBody()[0].getAccountNumber());
        assertEquals(ownerName, response.getBody()[0].getOwnerName());
    }

    @ParameterizedTest
    @CsvSource({
            "1001, Bob Smith"
    })
    void testGetBankAccountById(String accountNumber, String ownerName) {
        ResponseEntity<BankAccount> response = testRestTemplate.getForEntity(BASE_URL + "/1", BankAccount.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accountNumber, response.getBody().getAccountNumber());
        assertEquals(ownerName, response.getBody().getOwnerName());
    }

    @ParameterizedTest
    @CsvSource({
            "1011, Max Mustermann"
    })
    void testCreateBankAccount(String accountNumber, String ownerName) {
        String url = BASE_URL + "?accountNumber=" + accountNumber + "&ownerName=" + ownerName;
        ResponseEntity<BankAccount> response = testRestTemplate.postForEntity(url, null, BankAccount.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(accountNumber, response.getBody().getAccountNumber());
        assertEquals(ownerName, response.getBody().getOwnerName());

        testBankAccounts.add(response.getBody());
    }

    @ParameterizedTest
    @CsvSource({
            "1011, Max Mustermann"
    })
    void testDeposit(String accountNumber, String ownerName) {
        BankAccount testAkk = testRestTemplate.postForEntity(BASE_URL + "?accountNumber=" + accountNumber + "&ownerName=" + ownerName, null, BankAccount.class).getBody();
        double sumToDeposit = 100;
        String url = BASE_URL + "/11/deposit?amount=" + sumToDeposit;
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100, response.getBody());

        testBankAccounts.add(testAkk);
    }

    @ParameterizedTest
    @CsvSource({
        "1011, Max Mustermann"
    })
    void testWithdraw(String accountNumber, String ownerName) {
        BankAccount testAkk = testRestTemplate.postForEntity(BASE_URL + "?accountNumber=" + accountNumber + "&ownerName=" + ownerName, null, BankAccount.class).getBody();
        testRestTemplate.postForEntity(BASE_URL + "/1/deposit?amount=" + 200, null, Double.class);

        double sumToWithdraw = 100;
        String url = BASE_URL + "/11/withdraw?amount=" + sumToWithdraw;
        ResponseEntity<Double> response = testRestTemplate.postForEntity(url, null, Double.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100, response.getBody());

        testBankAccounts.add(testAkk);
    }
}
