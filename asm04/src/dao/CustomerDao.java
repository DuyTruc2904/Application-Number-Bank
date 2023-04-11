package dao;

import models.Customer;
import service.BinaryService;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class CustomerDao implements Serializable {
    private final static String FILE_PATH =  "store//customers.dat";

    public static void save(List<Customer> customers) throws IOException {
        BinaryService.writeFile(FILE_PATH, customers);
    }

    public static List<Customer> list() {
        return BinaryService.readFile(FILE_PATH);
    }

}
