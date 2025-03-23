package de.ait.javalessons.controller;

import de.ait.javalessons.model.BankAccount;
import de.ait.javalessons.repositories.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BankAccountControllerIT {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    BankAccountRepository bankAccountRepository;


    private static final String BASE_URL = "/accounts";
    private static final String ACCOUNT_NUMBER_1 = "1001";
    private static final String OWNER_NAME_1 = "Alice Johnson";
    private static final double BALANCE_1 = 2500.0;
    private static long ID_1;

    private static final String ACCOUNT_NUMBER_2 = "1002";
    private static final String OWNER_NAME_2 = "Charlie Brown";
    private static final double BALANCE_2 = 100.0;
    private static long ID_2;

    private static final String ACCOUNT_NUMBER_3 = "1003";
    private static final String OWNER_NAME_3 = "David White";
    private static final double BALANCE_3 = 4500.0;
    private static long ID_3;


    @BeforeEach
    void setUp() {
        BankAccount bankAccountOne = new BankAccount(ACCOUNT_NUMBER_1, OWNER_NAME_1, BALANCE_1);
        BankAccount bankAccountTwo = new BankAccount(ACCOUNT_NUMBER_2, OWNER_NAME_2, BALANCE_2);
        BankAccount bankAccountThree = new BankAccount(ACCOUNT_NUMBER_3, OWNER_NAME_3, BALANCE_3);

        // Clear the database and reinitialize test data
        bankAccountRepository.deleteAll();
        bankAccountRepository.save(bankAccountOne);
        bankAccountRepository.save(bankAccountTwo);
        bankAccountRepository.save(bankAccountThree);
        bankAccountRepository.save(new BankAccount("1004", "David White", 4500.0));
        bankAccountRepository.save(new BankAccount("1005", "Emma Green", 5500.0));
        bankAccountRepository.save(new BankAccount("1006", "Frank Black", 6500.0));
        bankAccountRepository.save(new BankAccount("1007", "Grace Adams", 7500.0));
        bankAccountRepository.save(new BankAccount("1008", "Henry Scott", 8500.0));
        bankAccountRepository.save(new BankAccount("1009", "Isabella Lee", 9500.0));
        bankAccountRepository.save(new BankAccount("1010", "Jack Wilson", 10500.0));

        ID_1 = bankAccountOne.getId();
        ID_2 = bankAccountTwo.getId();
        ID_3 = bankAccountThree.getId();

    }

    @Test
    @DisplayName("Get all bank accounts")
    void testGetBankAccountReturnDefaultAccounts() {
        ResponseEntity<BankAccount[]> response = testRestTemplate.getForEntity(BASE_URL, BankAccount[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(10, response.getBody().length);
        assertEquals(ACCOUNT_NUMBER_1, response.getBody()[0].getAccountNumber());
        assertEquals(OWNER_NAME_1, response.getBody()[0].getOwnerName());
    }

    @Test
    @DisplayName("Get a single bank account by id")
    void getBankAccountById() {
        String path = BASE_URL + "/" + ID_1;
        ResponseEntity<BankAccount> response = testRestTemplate.getForEntity(path, BankAccount.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ACCOUNT_NUMBER_1, response.getBody().getAccountNumber());
        assertEquals(OWNER_NAME_1, response.getBody().getOwnerName());
    }

    @ParameterizedTest
    @CsvSource({
            "1011, Jack Sparrow",
            "1012, Arnold Schwarzenegger",
            "1013, William Wolf"
    })
    @DisplayName("Add an account with success")
    void createBankAccount(String accountNumber, String ownerName) {
        String path = BASE_URL + "?accountNumber=" + accountNumber + "&ownerName=" + ownerName;
        ResponseEntity<BankAccount> response = testRestTemplate.postForEntity(path, null, BankAccount.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(accountNumber, response.getBody().getAccountNumber());
        assertEquals(ownerName, response.getBody().getOwnerName());
        assertEquals(0, response.getBody().getBalance());

    }


    @ParameterizedTest
    @CsvSource({
            "100, 2600",
            "500, 3000",
            "10.1, 2510.1"
    })

    @DisplayName("Deposit with success")
    void depositTestWithSuccess(double depositAmount, double expectedBalance) {
        String path = BASE_URL + "/" + ID_1 + "/deposit?amount=" + depositAmount;

        ResponseEntity<Double> response = testRestTemplate.postForEntity(path, null, Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBalance, response.getBody());
    }

    @ParameterizedTest
    @CsvSource({
            "500, 2000",
            "10.1, 2489.9",
    })
    @DisplayName("Withdraw with success")
    void withdrawWithSuccess(double withdrawAmount, double expectedBalance) {
        String paht = BASE_URL + "/" + ID_1 + "/withdraw?amount=" + withdrawAmount;
        ResponseEntity<Double> response = testRestTemplate.postForEntity(paht, null, Double.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBalance, response.getBody());
    }
}
