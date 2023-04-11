package dao;

import models.Transaction;
import service.BinaryService;
import java.io.IOException;
import java.util.List;

public class TransactionDao {
    private final static String FILE_PATH =  "store//transactions.dat";

    public static void save(List<Transaction> customers) throws IOException {
        BinaryService.writeFile(FILE_PATH, customers);
    }

    public static List<Transaction> list() {
        return BinaryService.readFile(FILE_PATH);
    }


}
