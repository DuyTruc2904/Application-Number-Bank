package in;

import models.Account;

public interface ITransfer {
    boolean transfer(Account account, Account accountReceive, double amount);

    boolean isTransfer(double amount);
}
