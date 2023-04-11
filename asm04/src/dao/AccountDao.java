package dao;

import models.Account;
import service.BinaryService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountDao {
    private final static String FILE_PATH =  "store//accounts.dat";
    private static final int MAX_THREAD = 1000;
    static ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD);
    public static void save(List<Account> accounts) throws IOException {
        BinaryService.writeFile(FILE_PATH, accounts);
    }

    public static List<Account> list() {
        return BinaryService.readFile(FILE_PATH);
    }


    public static void update(Account editAccount) throws IOException {
        List<Account> accounts = list();
        boolean hasExist = accounts.stream().anyMatch(account -> account.getAccountNumber().equals(editAccount.getAccountNumber()));
        final List<Account> updateAccounts = new ArrayList<>();
        if (hasExist) {
            for (Account account:accounts){
                if (account.getAccountNumber().equals(editAccount.getAccountNumber())){
                    executorService.submit(() -> {
                        updateAccounts.add(editAccount);
                    });
                } else {
                    executorService.submit(() -> {
                        updateAccounts.add(account);
                    });
                }
            }
            System.out.printf("\nUpdate Account [%s] success !", editAccount.getAccountNumber());

        } else {
            updateAccounts.addAll(accounts);
            System.out.printf("\nUpdate Account [%s] fail. Account not found !", editAccount.getAccountNumber());
        }
        save(updateAccounts);
    }
}


