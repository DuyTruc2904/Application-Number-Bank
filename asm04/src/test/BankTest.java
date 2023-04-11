package test;

import models.Bank;
import org.junit.jupiter.api.Test;
import java.io.Serializable;
import static org.junit.jupiter.api.Assertions.*;

class BankTest implements Serializable {

    private final Bank bank = new Bank();

    @Test
    void isCustomerExisted() {
        assertTrue(bank.isCustomerExisted("001111111111"));
        assertFalse(bank.isCustomerExisted("003333333"));
    }

    @Test
    void getCustomerById() {
        assertEquals("001111111111",bank.getCustomerById("001111111111").getCustomerld());
    }

    @Test
    void isAccountExisted() {
        assertTrue(bank.isAccountExisted("001111"));
    }

}