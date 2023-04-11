package test;

import models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.Serializable;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest implements Serializable {
    private Customer customer;
    @BeforeEach
    void setUp() {
        customer = new Customer("", "001111111111");
    }


    @Test
    void isPremium() {
        assertTrue(customer.isPremium());
    }

    @Test
    void getBalance() {
        assertEquals(545700000,customer.getBalance());
    }
}
