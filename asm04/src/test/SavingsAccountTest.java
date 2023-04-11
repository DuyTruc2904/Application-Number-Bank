package test;

import models.Account;
import models.DigitalBank;
import models.SavingsAccount;
import org.junit.jupiter.api.Test;
import java.io.Serializable;


import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest implements Serializable {
    DigitalBank activeBank = new DigitalBank();
    Account account1 = activeBank.getAcountByAccountNumber("001111");
    Account account2 = activeBank.getAcountByAccountNumber("002222");
    SavingsAccount savingsAccount1 = new SavingsAccount(account1.getName(), account1.getCustomerld(), account1.getAccountNumber(), account1.getBalance());

    @Test
    void withdraw() {
        assertTrue(savingsAccount1.withdraw(5000000));
        assertFalse(savingsAccount1.withdraw(600000000));
    }

    @Test
    void isAccepted() {
        assertTrue(savingsAccount1.withdraw(5000000));
        assertFalse(savingsAccount1.withdraw(6000));
        assertFalse(savingsAccount1.withdraw(45000001));
    }

    @Test
    void tranfer() {
        assertTrue(savingsAccount1.transfer(account1, account2, 300000));
    }

    @Test
    void isTranfer() {
        assertFalse(savingsAccount1.isTransfer(510000000));
        assertTrue(savingsAccount1.isTransfer(50000000));
    }
}